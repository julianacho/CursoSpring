import { Injectable } from '@angular/core';
import { Cliente } from './cliente';
import { Observable, of } from 'rxjs';// Define el objeto que permite hacer la peticion del servicio

import { HttpClient, HttpHeaders } from '@angular/common/http'; // Permite hacer la consulta del json
import { map } from 'rxjs/operators';

@Injectable()
export class ClienteService {

  private urlEndPoint: string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }
  //Observable hace que la respuesta se de como un observable
  // Se establece con el patron observar, es decir esta atento a que pueda observar la peticion
  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    // map permite castear el json de respuesta a un objeto
    // pipe permite adicionar mas operadores
    return this.http.get(this.urlEndPoint).pipe(
      map(response => response as Cliente[])
    );
    // Mapea y cambia la respuesta a un tipo Cliente[]
    //return this.http.get<Cliente[]>(this.urlEndPoint);
  }

  /**
   * Retorna el observable de tipo cliente
   * headers: Es el http en donde se envia en el cuepro del mensaje
   */
  create(cliente: Cliente) : Observable<Cliente> {
    return this.http.post<Cliente>(this.urlEndPoint, cliente, {headers: this.httpHeaders})
  }

  /**
   * pasa la url con `${this.urlEndPoint}/${id}`
   */
  getCliente(id): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`)
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.urlEndPoint}/${cliente.id}`, cliente, {headers: this.httpHeaders})
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlEndPoint}/${id}`, {headers: this.httpHeaders})
  }
}
