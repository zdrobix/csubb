import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Service } from '../services/user/service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private userService: Service, private router: Router) {}

  canActivate(): boolean {
    const token = this.userService.getToken();
    if (!token)
    {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

}