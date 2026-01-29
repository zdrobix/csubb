import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import * as L from 'leaflet';
import { IonHeader, IonToolbar, IonTitle, IonButtons, IonButton, IonContent } from "@ionic/angular/standalone";
import { AnimationController } from '@ionic/angular';

L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'assets/marker-icon-2x.png',
  iconUrl: 'assets/marker-icon.png',
  shadowUrl: 'assets/marker-shadow.png',
});

export const customModalEnterAnimation = (baseEl: HTMLElement) => {
  const animationCtrl = new AnimationController();

  const backdropAnimation = animationCtrl.create()
    .addElement(baseEl.querySelector('ion-backdrop')!)
    .fromTo('opacity', 0, 0.4);

  const wrapperAnimation = animationCtrl.create()
    .addElement(baseEl.querySelector('.modal-wrapper')!)
    .fromTo('transform', 'translateY(100%)', 'translateY(0%)')
    .fromTo('opacity', 0, 1);

  return animationCtrl.create()
    .addElement(baseEl)
    .duration(300)
    .easing('ease-out')
    .addAnimation([backdropAnimation, wrapperAnimation]);
};

export const customModalLeaveAnimation = (baseEl: HTMLElement) => {
  const animationCtrl = new AnimationController();

  const backdropAnimation = animationCtrl.create()
    .addElement(baseEl.querySelector('ion-backdrop')!)
    .fromTo('opacity', 0.4, 0);

  const wrapperAnimation = animationCtrl.create()
    .addElement(baseEl.querySelector('.modal-wrapper')!)
    .fromTo('transform', 'translateY(0px)', 'translateY(200px)')
    .fromTo('opacity', 1, 0)

  return animationCtrl.create()
    .addElement(baseEl)
    .duration(500)  
    .easing('cubic-bezier(0.25, 0.8, 0.25, 1)') 
    .addAnimation([backdropAnimation, wrapperAnimation]);
};

@Component({
  selector: 'app-select-location-modal',
  templateUrl: './select-location-modal.component.html',
  styleUrls: ['./select-location-modal.component.scss'],
  imports: [IonHeader, IonToolbar, IonTitle, IonButtons, IonButton, IonContent],
})
export class SelectLocationModalComponent implements OnInit {

  map!: L.Map;
  marker!: L.Marker;

  constructor(private modalCtrl: ModalController) { }

  ngOnInit() {}

  ionViewDidEnter() {
    this.initMap();
  }

  initMap() {
    this.map = L.map('map').setView([45.9432, 24.9668], 7);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);

    this.map.on('click', (e: L.LeafletMouseEvent) => {
      const { lat, lng } = e.latlng;
      if (this.marker) {
        this.marker.setLatLng([lat, lng]);
      } else {
        this.marker = L.marker([lat, lng]).addTo(this.map);
      }

      this.modalCtrl.dismiss({ lat, lng });
    });
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}

