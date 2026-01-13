import { Component, inject, signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../api.service'; // Asegúrate de tener este servicio con los métodos necesarios
import { DatePipe } from '@angular/common'; // <--- Añade esto

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [FormsModule, DatePipe],
  template: `
    <div class="page-container">
      <div class="booking-card">
        <h3>Nueva Reserva</h3>
        <div class="booking-form">
          <div class="form-row">
            <select [(ngModel)]="nuevaReserva.usuarioId">
               <option value="" disabled selected>Seleccionar Usuario</option>
               @for (u of usuarios(); track u.id) { <option [value]="u.id">{{u.nombre}}</option> }
            </select>
            <select [(ngModel)]="nuevaReserva.recursoId">
               <option value="" disabled selected>Seleccionar Recurso</option>
               @for (r of recursos(); track r.id) { <option [value]="r.id">{{r.nombre}}</option> }
            </select>
          </div>
          
          <div class="form-row">
            <div class="date-input">
              <label>Desde:</label>
              <input type="date" [(ngModel)]="nuevaReserva.fechaInicio">
            </div>
            <div class="date-input">
              <label>Hasta:</label>
              <input type="date" [(ngModel)]="nuevaReserva.fechaFin">
            </div>
          </div>

          <button class="btn btn-reserve" (click)="reservar()">
            Confirmar Reserva
          </button>
        </div>
      </div>

      <div class="history-section">
        <h3>Historial de Reservas</h3>
        <div class="list">
          @for (res of reservas(); track res.id) {
            <div class="list-item">
              <div class="item-info">
                <strong>{{ res.nombreUsuario }}</strong> reservó <span>{{ res.nombreRecurso }}</span>
              </div>
              <div class="item-dates">
                {{ res.fechaInicio | date:'dd/MM/yyyy' }} al {{ res.fechaFin | date:'dd/MM/yyyy' }}
              </div>
              <div class="item-status">Confirmada</div>
            </div>
          }
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-container { max-width: 900px; margin: 0 auto; padding: 20px; }
    .booking-card { background: #1e293b; color: white; padding: 30px; border-radius: 20px; margin-bottom: 30px; box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1); }
    .booking-form { display: flex; flex-direction: column; gap: 20px; margin-top: 20px; }
    
    .form-row { display: flex; gap: 15px; }
    .date-input { display: flex; flex-direction: column; gap: 5px; flex: 1; }
    .date-input label { font-size: 0.8rem; color: #94a3b8; }

    select, input { 
      border-radius: 10px; 
      border: 1px solid #334155; 
      padding: 12px; 
      background-color: #f8fafc;
      color: #1e293b; 
      width: 100%;
      font-size: 0.95rem;
    }
    
    select option { background: white; color: #1e293b; }

    .btn { padding: 15px; border-radius: 12px; border: none; font-weight: 700; color: white; cursor: pointer; transition: 0.3s; }
    .btn-reserve { background: linear-gradient(135deg, #6366f1, #4f46e5); font-size: 1.1rem; }
    .btn-reserve:hover { transform: translateY(-2px); box-shadow: 0 8px 20px rgba(99,102,241,0.4); }
    
    .list-item { background: white; margin-bottom: 12px; padding: 20px; border-radius: 12px; display: flex; align-items: center; justify-content: space-between; border: 1px solid #f1f5f9; }
    .item-info strong { color: #1e293b; }
    .item-dates { color: #64748b; font-size: 0.85rem; }
    .item-status { color: #10b981; font-weight: bold; font-size: 0.8rem; }
  `]
})
export class ReservasComponent implements OnInit {
  private api = inject(ApiService);

  usuarios = signal<any[]>([]);
  recursos = signal<any[]>([]);
  reservas = signal<any[]>([]);

  nuevaReserva = { usuarioId: '', recursoId: '', fechaInicio: '', fechaFin: '' };

  ngOnInit() { this.cargarDatos(); }

  cargarDatos() {
    this.api.getUsuarios().subscribe(d => this.usuarios.set(d));
    this.api.getRecursos().subscribe(d => this.recursos.set(d));
    this.api.getReservas().subscribe(d => this.reservas.set(d));
  }

  reservar() {
  if (!this.nuevaReserva.usuarioId || !this.nuevaReserva.recursoId || !this.nuevaReserva.fechaInicio) {
    alert('Por favor, completa todos los campos');
    return;
  }

  const reservaFormateada = {
    ...this.nuevaReserva,
    fechaInicio: this.nuevaReserva.fechaInicio + 'T00:00:00',
    fechaFin: this.nuevaReserva.fechaFin ? this.nuevaReserva.fechaFin + 'T23:59:59' : this.nuevaReserva.fechaInicio + 'T23:59:59'
  };

  this.api.postReserva(reservaFormateada).subscribe({
    next: () => {
      this.cargarDatos(); 
      this.nuevaReserva = { usuarioId: '', recursoId: '', fechaInicio: '', fechaFin: '' };
      alert('¡Reserva creada con éxito!');
    },
    error: (err) => {
      console.error('Error al reservar:', err);
      alert('Error en el servidor: ' + (err.error?.message || 'Revisa la consola'));
    }
  });
}
}