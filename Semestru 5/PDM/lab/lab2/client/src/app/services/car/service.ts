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
  public currentPage: number = 1;
  public pageSize: number = 5;

  constructor(private http: HttpClient) { 

  }

  loadLocalCars(): Car[] {
    const data = localStorage.getItem('cars');
    return data ? JSON.parse(data) : [];
  }

  getCarsNext(): Car[] {
    const cars = this.loadLocalCars();
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const next = cars.slice(startIndex, startIndex + this.pageSize);
    if (next.length > 0) {
      this.currentPage++;
    }
    return next;
  } 

  resetPagination() {
    this.currentPage = 1;
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

  saveNewCarLocally(car: Car) {
    const cars = this.loadLocalCars();
    cars.push(car);
    this.saveCarsToLocalStorage(cars);
  }

  addCar(car: Car): Observable<Car> {
    console.log('Adding car to server for user_id: ' + localStorage.getItem('user_id'));
    return this.http.post<Car>(`${environment.apiBaseUrl}/cars`, car).pipe(
      tap((addedCar) => {
        this.saveNewCarLocally(addedCar);
      }
    ));
  }
}
