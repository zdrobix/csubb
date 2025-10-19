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

  //  data: any
  //  cars$?: Observable<Car[]>;
  cars: Car[] = [];
  getAllSubscription?: Subscription;
  updateAllSubscription?: Subscription;

  selectedCar$?: Observable<Car>;

  constructor(private carService: CarService ) {}

  ngOnInit() {
    this.getAllSubscription = this.carService.getAllCars().subscribe(result => {
      // this.data = result;
      // const cars: Car[] = (this.data.data ?? []).map(
      //   (element: { 
      //     id: any; 
      //     name: any; 
      //     registration_number: any; 
      //     accident_count: any; 
      //   }) => ({
      //     id: parseInt(element.id, 10), 
      //     name: element.name, 
      //     registration_number: element.registration_number, 
      //     accident_count: element.accident_count
      //   })
      //   );
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