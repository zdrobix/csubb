import { Component, OnInit } from '@angular/core';
import { ModalController, IonicModule } from '@ionic/angular';
import { SelectLocationModalComponent } from '../select-location-modal/select-location-modal.component';
import { CommonModule } from '@angular/common';
import { trigger, state, style, animate, transition } from '@angular/animations';

@Component({
  selector: 'app-locate-elem',
  templateUrl: './locate-elem.component.html',
  styleUrls: ['./locate-elem.component.scss'],
  imports: [IonicModule, CommonModule],
  animations: [
    trigger('fadeSlide', [
      state('true', style({
        opacity: 1,
        transform: 'translateY(0)'
      })),
      state('false', style({
        opacity: 0,
        transform: 'translateY(-20px)'
      })),
      transition('true <=> false', [
        animate('300ms ease-out')
      ])
    ])
  ]
})
export class LocateElemComponent  implements OnInit {
  show = true;
  photo: string | null = null;

  constructor(private modalCtrl: ModalController) { }

  ngOnInit() {}


  toggle() {
    this.show = !this.show;
  }

  async openMap() {
      const modal = await this.modalCtrl.create({
        component: SelectLocationModalComponent,
      });
  
      modal.onDidDismiss().then((res) => {
        if (res.data) {
          this.photo = this.calculateNearestPhoto(res.data);
        }
      });
      return await modal.present();
  }

  calculateNearestPhoto(coord: {lat: number, lng: number} | null) {
    if (!coord) return;
    
    let albumStr = localStorage.getItem('album');
    if (!albumStr) return;
    let album = JSON.parse(albumStr);

    let min = Infinity;
    let photo;
    for (const item of album) {
      if (!item.location) continue;

      const dLat = item.location.lat - coord.lat;
      const dLng = item.location.lng - coord.lng;
      const dist = Math.sqrt(dLat * dLat + dLng * dLng);

      if (dist < min) {
        min = dist;
        photo = item.photo;
      }
    }
    return photo;
  }
}
