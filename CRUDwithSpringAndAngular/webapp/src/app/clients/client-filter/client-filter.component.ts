import {Component, OnInit, Pipe,PipeTransform} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-filter',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.css']
})

export class ClientFilterComponent implements OnInit{

  errorMessage: string;
  clients: Array<Client>;
  allClients: Array<Client>;
  filteredItems: Array<Client>;
  filteredAllClients: Array<Client>;
  selectedClient: Client;


  filterargs = {name: 'hello'};
  items = [{title: 'hello world'}, {title: 'hello kitty'}, {title: 'foo bar'}];


  constructor(private clientService: ClientService,
              private router: Router) { }

  ngOnInit(): void {
    this.getAllClients();
  }

  getAllClients() {
    this.clientService.getClients()
      .subscribe(
        allClients => this.allClients = allClients,
        error => this.errorMessage = <any>error
      );
  }


  filterClients(filter:string,size:string,page:string) {
    this.clientService.filterClients(filter,+size,+page)
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }




}

