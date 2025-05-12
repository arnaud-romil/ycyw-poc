import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as Stomp from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Message } from '../../models/message.interface';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserInfo } from '../../models/user-info.interface';
import { AuthService } from '../../core/auth.service';
import { ChatService } from '../../core/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit, OnDestroy {

  chatId!: string;
  stompClient: Stomp.Client | null = null;
  messages: Message[] = [];
  newMessage = '';
  userInfo!: UserInfo;

  constructor(private readonly route: ActivatedRoute, private readonly authService: AuthService, private readonly chatService: ChatService, private readonly router: Router) {
  }

  ngOnInit(): void {
    this.chatId = this.route.snapshot.paramMap.get('id')!;
    this.userInfo = JSON.parse(this.authService.getUserInfo());
    this.chatService.getChatMessages(this.chatId).subscribe({
      next: (chatMessages) => {
        this.messages.push(...chatMessages);
      }
    });

    this.connect();
  }

  ngOnDestroy(): void {
    this.stompClient?.deactivate();
  }

  connect() {
    const socket = new SockJS('/ws');
    this.stompClient = new Stomp.Client({
      webSocketFactory: () => socket,
      debug: str => console.log(str),
      reconnectDelay: 5000,
      onConnect: () => {
        this.stompClient?.subscribe(
          `/topic/chat/${this.chatId}`,
          message => {
            const msg = JSON.parse(message.body);
            this.messages.push(msg);
          }
        );
      },
      onStompError: frame => {
        console.error('Broker error:', frame);
      }
    });

    this.stompClient.activate();
  }

  sendMessage() {
    if (this.stompClient && this.stompClient.connected && this.newMessage.trim()) {
      const msg: Message = {
        sender: `${this.userInfo.email}`,
        content: this.newMessage
      };
      this.stompClient.publish({
        destination: `/app/chat/${this.chatId}`,
        body: JSON.stringify(msg)
      });
      this.newMessage = '';
    }
  }

  goBack() {
    this.router.navigate(['/home']);
  }

}
