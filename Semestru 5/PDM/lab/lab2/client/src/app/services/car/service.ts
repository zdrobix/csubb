import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, of, tap } from 'rxjs';
import { Car } from 'src/app/models/car.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Service {

  private carsSubject = new BehaviorSubject<Car[]>(this.loadLocalCars());
  public cars$ = this.carsSubject.asObservable();

  constructor(private http: HttpClient) { 

  }

  loadLocalCars(): Car[] {
    console.log('Loading cars from local storage for user_id: ' + localStorage.getItem('user_id'));
    const data = localStorage.getItem('cars');
    return data ? JSON.parse(data) : [];
  }

  getCars(): Observable<Car[]> {
    console.log('Getting cars from server for user_id: ' + localStorage.getItem('user_id'));
    return this.http.get<Car[]>(`${environment.apiBaseUrl}/cars/${localStorage.getItem('user_id')!}`);
  }

  getCarsPage(page: number, pageSize: number): Car[] {
    const cars = this.loadLocalCars();
    const startIndex = (page - 1) * pageSize;
    return cars.slice(startIndex, startIndex + pageSize);
  } 

  private saveCarsToLocalStorage(cars: Car[]) {
    localStorage.setItem('cars', JSON.stringify(cars));
    this.carsSubject.next(cars);
  }

  fetchCarsFromServer(): Observable<Car[]> {
    console.log('Fetching cars from server for user_id: ' + localStorage.getItem('user_id'));
    const user_id = localStorage.getItem('user_id');
    if (!user_id) {
      return of([]);
    }
    return this.http.get<Car[]>(`${environment.apiBaseUrl}/cars/${user_id}`).pipe(
      tap(cars => {
        this.saveCarsToLocalStorage(cars);
      })
    );
  }

  addCar(car: Car) {
    return this.http.post<Car>(`${environment.apiBaseUrl}/cars`, car).subscribe(newCar => {
      const cars = this.loadLocalCars();
      cars.push(newCar);
      this.saveCarsToLocalStorage(cars);
    });
  }
}
