import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class RecursosService {
  private http = inject(HttpClient);
  private URL_API = 'http://localhost:8092/api/v1/recursos'; 

  getRecursos() {
    return this.http.get<any[]>(this.URL_API);
  }

  postRecurso(usuario: any) {
    return this.http.post(this.URL_API, usuario);
  }
}