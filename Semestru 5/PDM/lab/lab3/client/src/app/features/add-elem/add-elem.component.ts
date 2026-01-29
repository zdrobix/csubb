import { Component } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { SelectLocationModalComponent } from '../select-location-modal/select-location-modal.component';
import { CommonModule } from '@angular/common';
import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import { Filesystem, Directory } from '@capacitor/filesystem';
import { Router } from '@angular/router';



@Component({
  selector: 'app-add-elem',
  templateUrl: './add-elem.component.html',
  styleUrls: ['./add-elem.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class AddElemComponent  {
  selectedLocation: { lat: number; lng: number } | null = null;
  photo: string | null = null;

  constructor(private modalCtrl: ModalController, private router: Router) {}

  async openMap() {
    const modal = await this.modalCtrl.create({
      component: SelectLocationModalComponent
    });

    modal.onDidDismiss().then((res) => {
      if (res.data) {
        this.selectedLocation = res.data;
      }
    });

    return await modal.present();
  }

  async takePhoto() {
    try {
      const image = await Camera.getPhoto({
        quality: 90,
        allowEditing: false,
        resultType: CameraResultType.DataUrl, 
        source: CameraSource.Camera,
      });
      this.photo = image.dataUrl!
      const link = document.createElement('a');
      link.href = image.dataUrl!; 
      link.download = 'photo_image'; 
      document.body.appendChild(link);
      link.click(); 
      document.body.removeChild(link);
    } catch (error) {
      console.error('Camera error:', error);
    }
  }

  async savePhoto(base64Photo: string): Promise<string | null> {
  try {
    const fileName = `photo_${new Date().getTime()}.jpeg`;

    const savedFile = await Filesystem.writeFile({
      path: `photos/${fileName}`,
      data: base64Photo,      
      directory: Directory.Data, 
      recursive: true
    });

    console.log('Photo saved:', savedFile.uri);
    return savedFile.uri;
  } catch (err) {
    console.error('Error saving photo:', err);
    return null;
  }
}

  handleAdd() {
    if (!this.selectedLocation) {
      return;
    }
    if (!this.photo) {
      return;
    }

    const element = {
      location: this.selectedLocation,
      photo: this.photo,
      createdAt: new Date()
    };

    this.savePhoto(this.photo);

    const storedItems = localStorage.getItem('album');
    let items = storedItems ? JSON.parse(storedItems) : [];

    items.push(element);

    localStorage.setItem('album', JSON.stringify(items));
    console.log('Saved new elem.');

    this.router.navigateByUrl('/list');
  }

}
