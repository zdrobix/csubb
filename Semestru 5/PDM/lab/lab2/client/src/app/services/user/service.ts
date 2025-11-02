import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class Service {
  
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<{ token: string, user_id: number }> {
    return this.http.post<{ token: string, user_id: number }>(`${environment.apiBaseUrl}/auth/login`, { username, password });
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  saveUserId(user_id: number) {
    localStorage.setItem('user_id', user_id.toString());
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getUserId() {
    return localStorage.getItem('user_id');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('cars');
    window.location.reload();
  }
}
