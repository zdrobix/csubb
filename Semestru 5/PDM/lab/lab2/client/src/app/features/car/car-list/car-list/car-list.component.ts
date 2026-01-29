import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of, Subscription } from 'rxjs';
import { Car } from 'src/app/models/car.model';
import { Service } from 'src/app/services/car/service';
import { Service as UserService } from 'src/app/services/user/service';
import { WebSocketService } from 'src/app/services/car/websocket.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class CarListComponent  implements OnInit, OnDestroy {

  cars: Car[] = [];

  webSocketSubscription?: Subscription;
  getAllSubscription?: Subscription;
  updateAllSubscription?: Subscription;
  selectedCar$?: Observable<Car>;
  userStatus$ = this.userService.onlineStatus$;

  constructor(private userService: UserService, private carService: Service, private wsService: WebSocketService, private router: Router) { }
  
  ngOnInit() {
    console.log('CarListComponent initialized for user_id: ' + localStorage.getItem('user_id'));

    this.getAllSubscription = this.carService.fetchCarsFromServer().subscribe({
      next: () => {
        const allCars = this.carService.loadLocalCars();
        this.cars = this.carService.getCarsNext();
      },
      error: (err) => {
        const allCars = this.carService.loadLocalCars();
        this.cars = this.carService.getCarsNext();
        console.error('Failed to fetch cars from server:', err);
      }
    });

    this.webSocketSubscription = this.wsService.messages$?.subscribe(msg => {
      switch (msg.type) {
        case 'car_added': {
          this.cars.push(msg.payload);
          break;
        }
        case 'car_updated': {
          const index = this.cars.findIndex(c => c._id === msg.payload._id);
          if (index !== -1) {
            this.cars[index] = msg.payload;
          }
          break;
        }
        case 'car_deleted': {
          this.cars = this.cars.filter(c => c._id !== msg.payload._id);
          break;
        }
      }
    });
  }

  onScroll() {
    const nextCars = this.carService.getCarsNext();
    if (nextCars.length > 0) {
      this.cars = [...this.cars, ...nextCars];
    }
  }

  onScrollContainer(event: any) {
    const element = event.target;
    const atBottom = element.scrollHeight - element.scrollTop <= element.clientHeight + 50;

    if (atBottom) {
      this.onScroll();
    }
  }

  ngOnDestroy(): void {
    this.getAllSubscription?.unsubscribe();
    this.updateAllSubscription?.unsubscribe();
    this.webSocketSubscription?.unsubscribe();
    this.wsService.disconnect();
  }

  setInfoCar(car: any) {
    this.selectedCar$ = of(car);
  }

  goToSearch() {
    this.router.navigate(['/cars/search']);
  }

  goToAccount() {
    this.router.navigate(['/account']);
  }

  goToAlbum() {
    this.router.navigate(['/album']);
  }

  goToMaps() {
    this.router.navigate(['/maps']);
  }

  goToAdd() {
    this.router.navigate(['/cars/add']);
  }
}
