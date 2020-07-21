import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from '@angular/common';
import {Client} from "../shared/client.model";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-new.component.html',
  styleUrls: ['./client-new.component.css']
})
export class ClientNewComponent implements OnInit {

  client: Client;


  constructor(private clientService: ClientService,
              private location: Location
  ) { }

  ngOnInit(): void {
  }

  saveClient(serialNumber: string, name: string, age: string) {
    console.log("saving student", serialNumber, name, age);

    this.clientService.saveClient({
      id: 0,
      serialNumber,
      name,
      age: +age
    })
      .subscribe(student => console.log("saved student: ", student));

    this.location.back(); // ...
  }

}
