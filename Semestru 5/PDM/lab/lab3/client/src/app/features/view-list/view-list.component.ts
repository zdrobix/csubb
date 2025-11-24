import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { customModalEnterAnimation, customModalLeaveAnimation, SelectLocationModalComponent } from '../select-location-modal/select-location-modal.component';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';


interface SavedItem {
  photo: string; 
  location: { lat: number; lng: number } | null;
}

@Component({
  selector: 'app-view-list',
  templateUrl: './view-list.component.html',
  styleUrls: ['./view-list.component.scss'],
  standalone:true,
  imports: [CommonModule, IonicModule],
})
export class ViewListComponent implements OnInit{
  items: SavedItem[] = [];

  constructor(private modalCtrl: ModalController) { }


  ngOnInit() {
    this.loadItems();
  }
  
  loadItems() {
    const saved = localStorage.getItem('album');
    if (saved) {
      try {
        this.items = JSON.parse(saved);
        console.log(this.items);
      } catch (e) {
        console.error('Failed to parse items from localStorage', e);
        this.items = [];
      }
    }
  }

  async editItemLocation(index: number) {
    const modal = await this.modalCtrl.create({
          component: SelectLocationModalComponent,
          leaveAnimation: customModalLeaveAnimation
        });
    
        modal.onDidDismiss().then((res) => {
          if (res.data) {
            let albumStr = localStorage.getItem('album');
            if (!albumStr) return;
            let album = JSON.parse(albumStr);
            album[index].location = res.data;
            
            localStorage.setItem('album', JSON.stringify(album));
            this.items = album;
          }
        });
    
        return await modal.present();
  }

  async editItemPhoto(index: number) {
    try {
      const image = await Camera.getPhoto({
        quality: 90,
        allowEditing: false,
        resultType: CameraResultType.DataUrl, 
        source: CameraSource.Camera,
      });
      const photo = image.dataUrl!
      if (photo) {
        let albumStr = localStorage.getItem('album');
        if (!albumStr) return;
        let album = JSON.parse(albumStr);
        album[index].photo = photo;
            
        localStorage.setItem('album', JSON.stringify(album));
        this.items = album;
      }
    } catch (error) {
      console.error('Camera error:', error);
    }
  }
}
