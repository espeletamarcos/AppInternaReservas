import { Component, inject, signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../usuarios/usuario.service'; // Ajusta la ruta

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="page-container">
      <div class="header">
        <h2>👥 Gestión de Usuarios</h2>
        <p>Administra los usuarios que pueden realizar reservas</p>
      </div>

      <div class="card form-card">
        <div class="form-grid">
          <div class="input-group">
            <label>Nombre</label>
            <input [(ngModel)]="nuevo.nombre" placeholder="Ej: Juan Pérez">
          </div>
          <div class="input-group">
            <label>Email</label>
            <input [(ngModel)]="nuevo.email" placeholder="juan@correo.com">
          </div>
          <button class="btn btn-save" (click)="guardar()" [disabled]="!nuevo.nombre || !nuevo.email">
            Guardar Usuario
          </button>
        </div>
      </div>

      <div class="card">
        <table class="custom-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Email</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            @for (u of lista(); track u.id) {
              <tr>
                <td>#{{ u.id }}</td>
                <td class="bold">{{ u.nombre }}</td>
                <td>{{ u.email }}</td>
                <td><span class="badge active">Activo</span></td>
              </tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `,
  styles: [`
    .page-container { max-width: 1000px; margin: 0 auto; display: flex; flex-direction: column; gap: 24px; }
    .header h2 { margin-bottom: 4px; color: #1e293b; }
    .header p { color: #64748b; margin: 0; }
    .card { background: white; padding: 24px; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.05); }
    .form-grid { display: grid; grid-template-columns: 1fr 1fr auto; gap: 16px; align-items: flex-end; }
    .input-group { display: flex; flex-direction: column; gap: 8px; }
    .input-group label { font-size: 0.85rem; font-weight: 600; color: #475569; }
    input { padding: 12px; border: 2px solid #e2e8f0; border-radius: 10px; font-size: 1rem; transition: 0.2s; }
    input:focus { border-color: #2563eb; outline: none; box-shadow: 0 0 0 4px rgba(37,99,235,0.1); }
    
    .btn { padding: 12px 24px; border-radius: 10px; border: none; font-weight: 700; cursor: pointer; transition: 0.3s; color: white; display: flex; align-items: center; gap: 8px; }
    .btn-save { background: linear-gradient(135deg, #2563eb, #1d4ed8); }
    .btn:hover:not(:disabled) { transform: translateY(-2px); box-shadow: 0 8px 15px rgba(37,99,235,0.3); }
    .btn:disabled { background: #cbd5e1; cursor: not-allowed; }

    .custom-table { width: 100%; border-collapse: collapse; }
    .custom-table th { text-align: left; padding: 12px; color: #64748b; border-bottom: 2px solid #f1f5f9; font-size: 0.8rem; }
    .custom-table td { padding: 16px 12px; border-bottom: 1px solid #f1f5f9; }
    .bold { font-weight: 600; color: #1e293b; }
    .badge { padding: 4px 10px; border-radius: 20px; font-size: 0.75rem; font-weight: 700; background: #dcfce7; color: #166534; }
  `]
})
export class UsuariosComponent implements OnInit {
  private service = inject(UsuarioService);
  lista = signal<any[]>([]);
  nuevo = { nombre: '', email: '', activo: true };

  ngOnInit() { this.cargar(); }
  cargar() { this.service.getUsuarios().subscribe(res => this.lista.set(res)); }
  guardar() { 
    this.service.postUsuario(this.nuevo).subscribe(() => {
      this.cargar();
      this.nuevo = { nombre: '', email: '', activo: true };
    });
  }
}