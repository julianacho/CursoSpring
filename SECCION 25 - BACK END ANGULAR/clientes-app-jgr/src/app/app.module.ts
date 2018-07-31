
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ClientesComponent } from './clientes/clientes.component';
import { DirectivaComponent } from './directiva/directiva.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { ClienteService } from './clientes/cliente.service';
import { RouterModule, Routes} from '@angular/router'; // Esablece las 
import { HttpClientModule } from '@angular/common/http';  // <-- NgModel lives here permite que sirva el ngmoddel

/**
 * Establece las rutas del proyecto
 */
const routes: Routes = [
  {path: '', redirectTo: '/clientes', pathMatch: 'full'},// Este path hace la direccion del home es decir redirige a clientes, hace match completo con la url
                                                        // de lo contrario solo lo hace para el componente
  {path: 'directivas', component: DirectivaComponent},// Este define la ruta directivas e indica que el componente es DirectivaComponent
  {path: 'clientes', component: ClientesComponent}, // Este define la ruta directivas e indica que el componente es ClientesComponent
];

@NgModule({
  declarations: [
    AppComponent,
    ClientesComponent,
    DirectivaComponent,
    FooterComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(routes) // Indica las rutas que se van a implementar en el proyecto
  ],
  providers: [ClienteService],
  bootstrap: [AppComponent]
})
export class AppModule { }
