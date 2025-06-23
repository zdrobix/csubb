import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { GameComponent } from './features/game/game.component';
import { RanksComponent } from './features/ranks/ranks.component';
import { ConfigComponent } from './features/config/config.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    GameComponent,
    RanksComponent,
    ConfigComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
