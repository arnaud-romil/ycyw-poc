import { Component, OnInit } from '@angular/core';
import { UserInfo } from '../../models/user-info.interface';
import { AuthService } from '../../core/auth.service';
import { Router } from '@angular/router';
import { ChatService } from '../../core/chat.service';
import { CommonModule, DatePipe } from '@angular/common';
import { Chat } from '../../models/chat.interface';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  userInfo!: UserInfo;
  activeChats: Chat[] = [];

  constructor(private readonly authService: AuthService, private readonly chatService: ChatService, private readonly router: Router) {
  }
  ngOnInit(): void {
    this.userInfo = JSON.parse(this.authService.getUserInfo());
    if (this.userInfo.role === 'SUPPORT') {
      this.chatService.getActiveChats().subscribe({
        next: (chats) => {
          this.activeChats = chats;
        },
        error: (err) => {
          console.error('Error fetching active chats:', err);
        }
      });
    }

  }

  startChat() {
    if (this.userInfo.role === 'CUSTOMER') {
      this.chatService.createChat().subscribe({
        next: (chat) => {
          const chatId = chat.id;
          this.router.navigate(['/chat', chatId]);
        },
        error: (err) => {
          console.error('Error creatin chat:', err);
        }
      });
    }
  }

  openChat(chatId: number) {
    this.router.navigate(['/chat', chatId]);
  }

}
