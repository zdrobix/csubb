import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { UpdateChildRequest } from '../models/update-child-request.model';
import { AppServiceService } from '../services/app-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Child } from '../models/child.model';

@Component({
  selector: 'app-edit-child',
  templateUrl: './edit-child.component.html',
  styleUrls: ['./edit-child.component.css']
})
export class EditChildComponent implements OnDestroy {
childId: number;
  getChildSubscription?: Subscription;
  updateChildSubscription?: Subscription;
  updateChildRequest?: UpdateChildRequest;

  constructor(private service: AppServiceService,
    private router: Router, 
    private route: ActivatedRoute) {
    this.childId = Number(this.route.snapshot.paramMap.get('id')) || 0;
    if (this.childId === 0)
      return;
    this.getChildSubscription = this.service.getChild(this.childId).subscribe({
      next: (child: Child) => {
        this.updateChildRequest = {
          name: child.name,
          cnp: child.cnp
        }
      }
    });
  }

  updateChild() {
    if (!this.updateChildRequest) return;
    this.updateChildSubscription = this.service.updateChild(this.childId, this.updateChildRequest!).subscribe({
      next: (child: Child) => {
        console.log(child.name, " updated.")
        this.router.navigateByUrl('/main');
      }
    });
  }

  ngOnDestroy(): void {
    this.getChildSubscription?.unsubscribe();
  }
}
