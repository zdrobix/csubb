import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { environment } from 'src/app/environment/environment';
import { LoginRequest } from '../models/login-request.model';
import { Child } from '../models/child.model';
import { Event } from '../models/event.model';
import { AddChildRequest } from '../models/add-child-request.model';
import { AddEventRequest } from '../models/add-event-request.model';
import { UpdateEventRequest } from '../models/update-event-request.model';
import { UpdateChildRequest } from '../models/update-child-request.model';
import { AddSignupRequest } from '../models/add-signup-request.model';
import { Signup } from '../models/signup.model';

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  constructor(private http: HttpClient) { }

  login (request: LoginRequest) : Observable<{token: string}> {
    return this.http.post<{token: string}>(`${environment.apiBaseUrl}/api/logininfo/login`, request)
      .pipe(
        tap(response => {
          localStorage.setItem('jwt_token', response.token);
        })
      );
  }

  logout() : void {
    localStorage.removeItem('jwt_token');
  }

  getChildren() : Observable<Child[]> {
    return this.http.get<Child[]>(`${environment.apiBaseUrl}/api/child`)
  }

  getEvents() : Observable<Event[]> {
    return this.http.get<Event[]>(`${environment.apiBaseUrl}/api/event`)
  }

  addChild(request: AddChildRequest) : Observable<Child> {
    return this.http.post<Child>(`${environment.apiBaseUrl}/api/child`, request)
  }

  getChild(id: number) : Observable<Child> {
    return this.http.get<Child>(`${environment.apiBaseUrl}/api/child/${id}`)
  }

  updateChild(id: number, request: UpdateChildRequest) : Observable<Child> {
    return this.http.put<Child>(`${environment.apiBaseUrl}/api/child/${id}`, request)
  }

  addEvent(request: AddEventRequest) : Observable<Event> {
    return this.http.post<Event>(`${environment.apiBaseUrl}/api/event`, request)
  }

  deleteEvent(id: number) : Observable<Event> {
    return this.http.delete<Event>(`${environment.apiBaseUrl}/api/event/${id}`)
  }

  getEvent(id: number) : Observable<Event> {
    return this.http.get<Event>(`${environment.apiBaseUrl}/api/event/${id}`)
  }

  updateEvent(id: number, request: UpdateEventRequest) : Observable<Event> {
    return this.http.put<Event>(`${environment.apiBaseUrl}/api/event/${id}`, request)
  }

  addSignup(request: AddSignupRequest) : Observable<Signup> {
    return this.http.post<Signup>(`${environment.apiBaseUrl}/api/signup`, request)
  }

  getSignups() : Observable<Signup[]> {
    return this.http.get<Signup[]>(`${environment.apiBaseUrl}/api/signup`)
  }
}
