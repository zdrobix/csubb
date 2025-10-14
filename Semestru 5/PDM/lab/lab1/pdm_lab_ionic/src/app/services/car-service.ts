import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { io, Socket } from 'socket.io-client';
import { Observable } from 'rxjs';
import { Car } from '../models/Car';

@Injectable({
  providedIn: 'root'
})
export class CarService implements OnDestroy {
  
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

  listenForUpdates() {
    return new Observable((subscriber) => {
      this.socket.on('carsUpdate', (data: Car[]) => {
        subscriber.next(data);
      });
   });
  }

  getAllCars() {
    return this.http.get<{ array: any[] }>(`${environment.apiBaseUrl}/api/cars`);
  }
}
