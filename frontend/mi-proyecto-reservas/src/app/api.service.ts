import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  // Inyección moderna sin constructor
  private http = inject(HttpClient);
  
  // URL base de tu backend Spring Boot
  private baseUrl = 'http://localhost:8092/api/v1';

  // --- MÉTODOS PARA USUARIOS ---
  getUsuarios(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/usuarios`);
  }

  postUsuario(usuario: any): Observable<any> {
    // Recuerda que aquí enviamos el objeto que Spring espera (nombre, email, activo)
    return this.http.post(`${this.baseUrl}/usuarios`, usuario);
  }

  // --- MÉTODOS PARA RECURSOS ---
  getRecursos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/recursos`);
  }

  postRecurso(recurso: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/recursos`, recurso);
  }

  // --- MÉTODOS PARA RESERVAS ---
  getReservas(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reservas`);
  }

  postReserva(reserva: any): Observable<any> {
    /* IMPORTANTE: Aquí sueles enviar algo como:
       { usuarioId: 1, recursoId: 5, fecha: '2024-12-01' }
    */
    return this.http.post(`${this.baseUrl}/reservas`, reserva);
  }
}