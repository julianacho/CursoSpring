import { Injectable } from '@angular/core';
import { Cliente } from './cliente';
import { Observable, of } from 'rxjs';// Define el objeto que permite hacer la peticion del servicio

import { HttpClient } from '@angular/common/http'; // Permite hacer la consulta del json
import { map } from 'rxjs/operators';

@Injectable()
export class ClienteService {

  private urlEndPoint: string = 'http://localhost:8090/api/clientes';
  constructor(private http: HttpClient) { }

  //Observable hace que la respuesta se de como un observable
  // Se establece con el patron observar, es decir esta atento a que pueda observar la peticion
  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    // map permite castear el json de respuesta a un objeto
    // pipe permite adicionar mas operadores
    return this.http.get(this.urlEndPoint).pipe(
      map((response) => {response as Cliente[]})
      //map((response) => {response as Cliente[]})
    );

    // Mapea y cambia la respuesta a un tipo Cliente[]
    //return this.http.get<Cliente[]>(this.urlEndPoint);
  }
}
