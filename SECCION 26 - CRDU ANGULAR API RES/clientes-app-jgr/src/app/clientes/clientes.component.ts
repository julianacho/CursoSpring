import { Component, OnInit } from '@angular/core';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import swal from 'sweetalert2';

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

  delete(cliente: Cliente): void {
    swal({
      title: 'Está seguro?',
      text: `¿Seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido}?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      buttonsStyling: false,
      reverseButtons: true
    }).then((result) => {
      if (result.value) {

        this.clienteService.delete(cliente.id).subscribe(
          response => {
            this.clientes = this.clientes.filter(cli => cli !== cliente)
            swal(
              'Cliente Eliminado!',
              `Cliente ${cliente.nombre} eliminado con éxito.`,
              'success'
            )
          }
        )

      }
    })
  }

}
