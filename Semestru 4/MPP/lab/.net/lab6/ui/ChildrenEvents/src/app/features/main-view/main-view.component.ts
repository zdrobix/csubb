import { Component, OnDestroy, OnInit } from '@angular/core';
import { Child } from '../models/child.model';
import { Event } from '../models/event.model';
import { Observable, Subscription } from 'rxjs';
import { AppServiceService } from '../services/app-service.service';
import { AddChildRequest } from '../models/add-child-request.model';
import { AddEventRequest } from '../models/add-event-request.model';
import { Router } from '@angular/router';
import { Signup } from '../models/signup.model';
import { AddSignupRequest } from '../models/add-signup-request.model';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.css']
})
export class MainViewComponent implements OnInit, OnDestroy{
  children$?: Observable<Child[]>;
  events$?: Observable<Event[]>;
  signups$?: Observable<Signup[]>
  addChildRequest: AddChildRequest;
  addEventRequest: AddEventRequest;
  addSignupRequest?: AddSignupRequest;
  addChildSubscription?: Subscription;
  addEventSubscription?: Subscription;
  addSignupSubscription?: Subscription;
  deleteEventSubscription?: Subscription;

  constructor(private service: AppServiceService, private router: Router) {
    this.addChildRequest = {
      name: '',
      cnp: ''
    }
    this.addEventRequest = {
      name: '',
    }
  }

  addChild() {
    if (!this.addChildRequest.name || !this.addChildRequest.cnp)
      return;
    this.addChildSubscription = this.service.addChild(this.addChildRequest).subscribe({
      next: (child: Child) => {
        this.children$ = this.service.getChildren();
      }
    });
    this.addChildRequest ={name: '', cnp: ''};
  }

  addEvent() {
    if (!this.addEventRequest.name || !this.addEventRequest.minAge || !this.addEventRequest.maxAge)
      return;
    this.addEventSubscription = this.service.addEvent(this.addEventRequest).subscribe({
      next: (event: Event) => {
        this.events$ = this.service.getEvents();
      }
    });
    this.addEventRequest = {name: ''}
  }

  deleteEvent(id: number) {
    this.deleteEventSubscription = this.service.deleteEvent(id).subscribe({
      next: (event: Event) => {
        console.log(event.name, " deleted.")
        this.events$ = this.service.getEvents();
      }
    });
  }

  logout() {
    this.service.logout();
    this.router.navigate(['/login']);
  }

  ngOnInit(): void {
    this.children$ = this.service.getChildren();
    this.events$ = this.service.getEvents();
  }

  
  ngOnDestroy(): void {
    this.addChildSubscription?.unsubscribe();
    this.addEventSubscription?.unsubscribe();
    this.deleteEventSubscription?.unsubscribe();
  }
}
