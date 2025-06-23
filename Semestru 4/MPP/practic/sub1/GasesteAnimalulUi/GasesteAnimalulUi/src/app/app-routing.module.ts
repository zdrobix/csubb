import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { GameComponent } from './features/game/game.component';
import { RanksComponent } from './features/ranks/ranks.component';
import { ConfigComponent } from './features/config/config.component';

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "game", component: GameComponent},
  {path: "config", component: ConfigComponent},
  {path: "ranks", component: RanksComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
