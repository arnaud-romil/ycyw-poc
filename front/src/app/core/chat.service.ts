import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chat } from '../models/chat.interface';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private readonly http: HttpClient) { }

  createChat(): Observable<Chat> {
    return this.http.post<Chat>('/api/chats', {});
  }

  getActiveChats(): Observable<Chat[]> {
    return this.http.get<Chat[]>('/api/chats');
  }

}
