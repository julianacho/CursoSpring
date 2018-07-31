import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[];

  // Se inyecta el servicio de cliente service
  constructor(private clienteService: ClienteService) { }

  // Al iniciar el componente
  /**
   * El observable es cliente
   */
  ngOnInit() {
    // Se debe suscribir el cliente por que va a ser observados por observadores
    //  (clientes) es el parametro de envio
    this.clienteService.getClientes().subscribe(
      // clientes es el resultado del estring convertido a un listado de clientes
       (clientes) => {
         // Es asincrono cuando termine asigna clientes a this.clientes
         this.clientes = clientes
        }
      // clientes =>this.clientes = clientes
         
    );
  }

}
