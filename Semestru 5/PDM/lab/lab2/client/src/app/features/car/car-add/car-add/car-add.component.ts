import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AddCarRequest } from 'src/app/models/add-car.model';
import { Service as UserService } from 'src/app/services/user/service';
import { Service as Service } from 'src/app/services/car/service';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/models/car.model';

@Component({
  selector: 'app-car-add',
  templateUrl: './car-add.component.html',
  styleUrls: ['./car-add.component.scss'],
})
export class CarAddComponent  implements OnDestroy {
  model: AddCarRequest;
  addCarSubscription?: Subscription;

  constructor(private userService: UserService, private carService: Service, private router: Router) {
    this.model = {
      id_user: Number(localStorage.getItem('user_id')!),
      name: '',
      registration_number: '',
      accident_count: null!,
      registration_date: new Date()
    }
  }

  onFormSubmit() {
    console.log('Form submitted with data:', this.model);
    const carToAdd: Car = {
      _id: undefined,
      id_user: this.model.id_user,
      name: this.model.name,
      registration_number: this.model.registration_number,
      accident_count: this.model.accident_count!,
      registration_date: this.model.registration_date
    };
    this.carService.saveNewCarLocally(carToAdd);
    if (this.userService.isOnline()) {
      this.addCarSubscription = this.carService.addCar(carToAdd).subscribe({
        next: (addedCar) => {
          console.log('Car successfully added to server:', addedCar);
        },
        error: (error) => {
          console.error('Error adding car to server:', error);
        }
      });
    } else {
      alert('You are offline. The car has been saved locally and will be synced when you go online.');
      return;
    }
    this.router.navigate(['/cars']);
  }

  ngOnDestroy() {
    this.addCarSubscription?.unsubscribe();
  }

}
