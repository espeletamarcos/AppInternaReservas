import { Routes } from '@angular/router';
import { UsuariosComponent } from './usuarios/usuarios';
import { RecursosComponent } from './recursos/recursos';
import { ReservasComponent } from './reservas/reservas';

export const routes: Routes = [
  { path: 'usuarios', component: UsuariosComponent },
  { path: 'recursos', component: RecursosComponent },
  { path: 'reservas', component: ReservasComponent },
  { path: '', redirectTo: '/usuarios', pathMatch: 'full' }, // Ruta por defecto
  { path: '**', redirectTo: '/usuarios' } // Si escriben cualquier cosa, a usuarios
];