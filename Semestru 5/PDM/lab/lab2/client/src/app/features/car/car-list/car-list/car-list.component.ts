import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, of, Subscription } from 'rxjs';
import { Car } from 'src/app/models/car.model';
import { Service } from 'src/app/services/car/service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.scss'],
})
export class CarListComponent  implements OnInit, OnDestroy {

  cars: Car[] = [];
  getAllSubscription?: Subscription;
  updateAllSubscription?: Subscription;
  selectedCar$?: Observable<Car>;

  constructor(private carService: Service) { }
  
  ngOnInit() {
    
  }

  ngOnDestroy(): void {
    this.getAllSubscription?.unsubscribe();
    this.updateAllSubscription?.unsubscribe();
  }

  setInfoCar(car: any) {
    this.selectedCar$ = of(car);
  }

}
