import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private http = inject(HttpClient);
  private URL_API = 'http://localhost:8092/api/v1/usuarios'; // Cambia esto por tu URL de Spring

  getUsuarios() {
    return this.http.get<any[]>(this.URL_API);
  }

  postUsuario(usuario: any) {
    return this.http.post(this.URL_API, usuario);
  }
}