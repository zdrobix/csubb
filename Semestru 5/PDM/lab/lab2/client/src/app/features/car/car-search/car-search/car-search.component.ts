import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Car } from 'src/app/models/car.model';
import { Service } from 'src/app/services/car/service';

@Component({
  selector: 'app-car-search',
  templateUrl: './car-search.component.html',
  styleUrls: ['./car-search.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class CarSearchComponent  implements OnInit {
  cars: Car[] = [];
  
  constructor(private carService: Service) { }

  ngOnInit() {
    this.carService.fetchCarsFromServer();
    this.cars = this.carService.loadLocalCars();
  }


  searchCars() {
    const nameInput = (<HTMLInputElement>document.getElementById('nameInput')).value;
    const registredAfterInput = (<HTMLInputElement>document.getElementById('registredAfterInput')).value;
    const registredBeforeInput = (<HTMLInputElement>document.getElementById('registredBeforeInput')).value;
    const accidentCountInput = (<HTMLInputElement>document.getElementById('accidentCountInput')).value;

    if (!nameInput && !registredAfterInput && !registredBeforeInput && !accidentCountInput) {
      this.cars = this.carService.loadLocalCars();
      return;
    }

    this.cars = this.carService.loadLocalCars().filter(car => {
      const matchesName = nameInput ? car.name.toLowerCase().includes(nameInput.toLowerCase()) : true;
      const matchesRegistredAfter = registredAfterInput ? new Date(car.registration_date) >= new Date(registredAfterInput) : true;
      const matchesRegistredBefore = registredBeforeInput ? new Date(car.registration_date) <= new Date(registredBeforeInput) : true;
      const matchesAccidentCount = accidentCountInput ? car.accident_count == parseInt(accidentCountInput) : true;
      return matchesName && matchesRegistredAfter && matchesRegistredBefore && matchesAccidentCount;
    });
  }
}
