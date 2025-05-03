import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest } from '../models/login-request.interface';
import { UserInfo } from '../models/user-info.interface';
import { LoginResponse } from '../models/login-response.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly apiUrl = 'api/auth';

  constructor(private readonly http: HttpClient) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          this.saveToken(response.accessToken);
          this.saveUser(response.user)
        })
      );
  }

  private saveToken(token: string): void {
    localStorage.setItem('jwt', token);
  }

  private saveUser(userInfo: UserInfo): void {
    localStorage.setItem('user', JSON.stringify(userInfo));
  }

  getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  getUserInfo(): string {
    if (localStorage.getItem('user')) {
      return localStorage.getItem('user') as string;
    }
    return '{}';
  }
}
