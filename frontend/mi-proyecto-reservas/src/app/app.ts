import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive], // Importante para la navegación
  template: `
    <nav style="background: #333; padding: 1rem; color: white;">
      <a routerLink="/usuarios" routerLinkActive="active" style="color: white; margin-right: 15px;">Usuarios</a>
      <a routerLink="/recursos" routerLinkActive="active" style="color: white; margin-right: 15px;">Recursos</a>
      <a routerLink="/reservas" routerLinkActive="active" style="color: white;">Reservas</a>
    </nav>

    <div style="padding: 20px;">
      <router-outlet></router-outlet>
    </div>

    <style>
      .active { font-weight: bold; border-bottom: 2px solid white; }
      a { text-decoration: none; }
    </style>
  `
})
export class AppComponent {}