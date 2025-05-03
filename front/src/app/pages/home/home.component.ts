import { Component } from '@angular/core';
import { UserInfo } from '../../models/user-info.interface';
import { AuthService } from '../../core/auth.service';
import { Router } from '@angular/router';
import { ChatService } from '../../core/chat.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  userInfo: UserInfo;

  constructor(private readonly authService: AuthService, private readonly chatService: ChatService, private readonly router: Router) {
    this.userInfo = JSON.parse(this.authService.getUserInfo());
  }

  startChat() {
    if (this.userInfo.role === 'CUSTOMER') {
      this.chatService.createChat().subscribe({
        next: (chat) => {
          const chatId = chat.id;
          console.log('Chat created with ID:', chatId);
          //this.router.navigate(['/chat', chatId]);
        },
        error: (err) => {
          console.error('Error creatin chat:', err);
        }
      });
    }
  }

}
