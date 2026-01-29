import { Component, Injectable, OnDestroy, OnInit } from '@angular/core';
import { Car } from '../models/car.model';
import { Observable, of, Subscription } from 'rxjs';
import { CarService } from '../services/car/car-service';

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

  //  data: any
  //  cars$?: Observable<Car[]>;
  cars: Car[] = [];
  getAllSubscription?: Subscription;
  updateAllSubscription?: Subscription;

  selectedCar$?: Observable<Car>;

  constructor(private carService: CarService ) {}

  ngOnInit() {
    this.getAllSubscription = this.carService.getAllCars().subscribe(result => {
      this.cars = result.data;
    });

    this.updateAllSubscription = this.carService.listenForUpdates().subscribe( payload => {
      this.cars = payload.data;
      this.selectedCar$ = of();
      console.log("Received update via WebSocket.");
     });
  }

  setInfoCar(car: any) {
    this.selectedCar$ = of(car);
  }

  ngOnDestroy() {
    this.getAllSubscription?.unsubscribe();
    this.updateAllSubscription?.unsubscribe();
  }

}