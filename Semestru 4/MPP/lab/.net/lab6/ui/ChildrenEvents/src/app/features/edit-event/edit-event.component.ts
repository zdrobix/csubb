import { Component, OnDestroy, OnInit } from '@angular/core';
import { UpdateEventRequest } from '../models/update-event-request.model';
import { AppServiceService } from '../services/app-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Event } from '../models/event.model';

@Component({
  selector: 'app-edit-event',
  templateUrl: './edit-event.component.html',
  styleUrls: ['./edit-event.component.css']
})
export class EditEventComponent implements OnDestroy {
  eventId: number;
  getEventSubscription?: Subscription;
  updateEventSubscription?: Subscription;
  updateEventRequest?: UpdateEventRequest;

  constructor(private service: AppServiceService,
    private router: Router, 
    private route: ActivatedRoute) {
    this.eventId = Number(this.route.snapshot.paramMap.get('id')) || 0;
    if (this.eventId === 0)
      return;
    this.getEventSubscription = this.service.getEvent(this.eventId).subscribe({
      next: (event: Event) => {
        this.updateEventRequest = {
          name: event.name,
          minAge: event.minAge,
          maxAge: event.maxAge
        }
      }
    });
  }

  updateEvent() {
    if (!this.updateEventRequest) return;
    this.updateEventSubscription = this.service.updateEvent(this.eventId, this.updateEventRequest!).subscribe({
      next: (event: Event) => {
        console.log(event.name, " updated.")
        this.router.navigateByUrl('/main');
      }
    });
  }

  ngOnDestroy(): void {
    this.getEventSubscription?.unsubscribe();
  }
}
