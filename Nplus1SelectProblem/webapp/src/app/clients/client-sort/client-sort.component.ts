import { Component, OnInit } from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-sort',
  templateUrl: './client-sort.component.html',
  styleUrls: ['./client-sort.component.css']
})
export class ClientSortComponent {

  errorMessage: string;
  clients: Array<Client>;
  allClients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private router: Router) { }



  filterUser(client: Client) {
    return client.age >= 50
  }


  sortClientsAngular() {
    this.clientService.getClients()
      .subscribe(
        allClients => this.allClients = allClients,
        error => this.errorMessage = <any>error
      );
  }

  sortClients(size:string,page:string,field:string,direction:string) {
    this.clientService.sortClients(+size,+page,field,direction)
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }


}
