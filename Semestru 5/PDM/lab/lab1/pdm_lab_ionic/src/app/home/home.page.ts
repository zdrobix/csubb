import { Component, Injectable, OnDestroy, OnInit } from '@angular/core';
import { Car } from '../models/Car';
import { Observable, of, Subscription } from 'rxjs';
import { CarService } from '../services/car-service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: false,
})
@Injectable({
  providedIn: 'root'
})
export class HomePage implements OnInit, OnDestroy {

  data: any
  cars$?: Observable<Car[]>;
  getAllSubscription?: Subscription;

  constructor(private carService: CarService ) {}

  ngOnInit() {
    this.getAllSubscription = this.carService.getAllCars().subscribe(result => {
      this.data = result;
      const cars: Car[] = (this.data.data ?? []).map(
        (element: { 
          id: any; 
          name: any; 
          registration_number: any; 
          had_accident: any; 
        }) => ({
          id: parseInt(element.id, 10), 
          name: element.name, 
          registration_number: element.registration_number, 
          had_accident: element.had_accident === 'true'
        })
      );
      this.cars$ = of(cars)
    });
  }

  ngOnDestroy() {
    this.getAllSubscription?.unsubscribe();
  }

}