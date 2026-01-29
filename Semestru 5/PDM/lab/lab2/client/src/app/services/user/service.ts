import { HttpClient } from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class Service {

  private onlineStatusSubject = new BehaviorSubject<boolean>(navigator.onLine);
  public onlineStatus$ = this.onlineStatusSubject.asObservable();
  
  constructor(private http: HttpClient, private zone: NgZone) { 
    window.addEventListener('online', () => {
      this.zone.run(() => {
        this.onlineStatusSubject.next(true);
      });
    });

    window.addEventListener('offline', () => {
      this.zone.run(() => {
        this.onlineStatusSubject.next(false);
      });
    });
  }    

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

  private updateAuthStatus(isAuthenticated: boolean) {
    this.onlineStatusSubject.next(isAuthenticated);
  }

  isOnline(): boolean {
    return this.onlineStatusSubject.value;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('cars');
    window.location.reload();
  }
}
