import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { io, Socket } from 'socket.io-client';
import { Observable } from 'rxjs';
import { LoginRequest } from 'src/app/models/login-request.model';
import { User } from 'src/app/models/user.model';


@Injectable({
  providedIn: 'root'
})
export class UserService implements OnDestroy {
  
  private socket: Socket;

  constructor(private http: HttpClient) { 
    this.socket = io(environment.apiBaseUrl, {
      transports: ['websocket'],
      withCredentials: false
    });
  }

  ngOnDestroy(): void {
    this.socket.disconnect();
  }

  login(model: LoginRequest): Observable<User> {

    throw new Error('Method not implemented.');
  }
}
