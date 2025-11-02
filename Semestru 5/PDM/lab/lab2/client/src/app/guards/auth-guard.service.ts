import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService {
    constructor() { }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('token');
    }
}