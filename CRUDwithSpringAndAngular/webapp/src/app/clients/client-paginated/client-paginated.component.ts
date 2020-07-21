import { Component, OnInit } from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-paginated',
  templateUrl: './client-paginated.component.html',
  styleUrls: ['./client-paginated.component.css']
})
export class ClientPaginatedComponent  {

  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private router: Router) { }



  getClientsPaginated(size:string,number:string) {
    this.clientService.getClientsPaginated(0+size,0+number)
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }
  filterClientsByName(){
    this.router.navigate(["client/filter"]);
  }

  // filterClients(filter:string) {
  //   this.clientService.filterClients(filter)
  //     .subscribe(
  //       clients => this.clients = clients,
  //       error => this.errorMessage = <any>error
  //     );
  // }

}
