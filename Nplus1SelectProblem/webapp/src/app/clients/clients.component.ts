import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent {

  constructor(private router: Router) {
  }


  addNewClient() {
    console.log("add new client btn clicked ");

    this.router.navigate(["client/new"]);
  }

  filterClientsByName(){
    this.router.navigate(["client/filter"]);
  }
  getClientsPaginated(){
    this.router.navigate(["client/paginated"]);
  }
  getClientsSorted(){
    this.router.navigate(["client/sorted"]);
  }



}
