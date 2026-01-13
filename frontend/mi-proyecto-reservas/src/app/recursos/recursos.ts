import { Component, inject, signal, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RecursosService } from './recursos.service';

@Component({
  selector: 'app-recursos',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="page-container">
      <div class="header">
        <h2>Inventario de Recursos</h2>
        <div class="quick-add card">
          <input [(ngModel)]="nuevo.nombre" placeholder="Nombre del recurso">
          <select [(ngModel)]="nuevo.tipoRecurso">
            <option value="">Tipo de recurso</option>
            <option value="SALA">Sala</option>
            <option value="EQUIPO">Equipo</option>
            <option value="MESA">Mesa</option>
          </select>
          <button class="btn btn-add" (click)="guardar()">
            Añadir
          </button>
        </div>
      </div>

      <div class="grid">
        @for (r of lista(); track r.id) {
          <div class="res-card">
            <div class="res-icon">
                @if (r.tipoRecurso === 'SALA') { 🏠 } 
                @else if (r.tipoRecurso === 'MESA') { 🪑 } 
                @else { 💻 }
            </div>
            <h4>{{ r.nombre }}</h4>
            <span class="res-type">{{ r.tipoRecurso }}</span>
          </div>
        }
      </div>
    </div>
  `,
  styles: [`
    .page-container { max-width: 1000px; margin: 0 auto; }
    .header { margin-bottom: 30px; }
    .quick-add { display: flex; gap: 12px; margin-top: 20px; align-items: center; }
    .card { background: white; padding: 16px; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); }
    input, select { padding: 10px; border: 1px solid #e2e8f0; border-radius: 8px; flex: 1; }
    
    .btn { padding: 10px 20px; border-radius: 8px; border: none; font-weight: 700; color: white; cursor: pointer; transition: 0.3s; }
    .btn-add { background: linear-gradient(135deg, #10b981, #059669); }
    .btn-add:hover { transform: scale(1.05); box-shadow: 0 5px 15px rgba(16,185,129,0.3); }

    .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 20px; }
    .res-card { background: white; padding: 24px; border-radius: 16px; text-align: center; border: 1px solid #f1f5f9; transition: 0.3s; }
    .res-card:hover { border-color: #10b981; transform: translateY(-5px); }
    .res-icon { font-size: 2.5rem; margin-bottom: 12px; }
    .res-type { font-size: 0.7rem; font-weight: 800; background: #f1f5f9; padding: 4px 12px; border-radius: 6px; color: #64748b; }
  `]
})
export class RecursosComponent implements OnInit {
  private service = inject(RecursosService);
  lista = signal<any[]>([]);
  nuevo = { nombre: '', tipoRecurso: '' };

  ngOnInit() { this.cargar(); }
  cargar() { this.service.getRecursos().subscribe(res => this.lista.set(res)); }
  guardar() { 
    this.service.postRecurso(this.nuevo).subscribe(() => {
      this.cargar();
      this.nuevo = { nombre: '', tipoRecurso: '' };
    });
  }
}