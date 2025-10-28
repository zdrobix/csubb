import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { io, Socket } from 'socket.io-client';
import { Observable } from 'rxjs';
import { Car } from '../../models/car.model';

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

  listenForUpdates(): Observable<{ data: Car[]}> {
    return new Observable((subscriber) => {
      this.socket.on('carsUpdate', (payload: { data: Car[]} ) => {
        subscriber.next(payload);
      });

      return () => {
        this.socket.off('carsUpdate');
      };
   });
  }

  getAllCars() {
    return this.http.get<{ data: Car[] }>(`${environment.apiBaseUrl}/api/cars`);
  }
}
