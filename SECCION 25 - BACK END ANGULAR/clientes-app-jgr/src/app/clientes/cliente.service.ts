import { Injectable } from '@angular/core';
import { Cliente } from './cliente';
import { Observable, of } from 'rxjs';// Define el objeto que permite hacer la peticion del servicio

import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable()
export class ClienteService {

  private urlEndPoint: string = 'http://localhost:8090/api/clientes';
  constructor(private http: HttpClient) { }

  //Observable hace que la respuesta se de como un observable
  // Se establece con el patron observar, es decir esta atento a que pueda observar la peticion
  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get(this.urlEndPoint).pipe(
      map(response => response as Cliente[])
    );
  }
}
