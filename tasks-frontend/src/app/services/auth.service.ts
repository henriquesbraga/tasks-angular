import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

interface LoginResponse {
  token: string;
  name: string;
}

interface RegisterResponse {
  status: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/auth/login`, { username, password })
      .pipe(
        tap((response) => {
          console.log('resp: ' + response);
          sessionStorage.setItem('token', response.token);
          sessionStorage.setItem('userName', response.name);
        })
      );
  }

  register(
    username: string,
    password: string,
    name: string
  ): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`${this.apiUrl}/users`, {
      username,
      password,
      name,
    });
  }

  logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userName');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getUserName(): string | null {
    return localStorage.getItem('userName');
  }
}
