import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Service } from 'src/app/services/user/service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss'],
})
export class LogoutComponent  implements OnInit {

  constructor( private userService: Service, private router: Router) { }

  ngOnInit() {}

  logout() {
    console.log('Logging out user_id: ' + localStorage.getItem('user_id'));
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
