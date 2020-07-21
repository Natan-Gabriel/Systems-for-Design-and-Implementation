import { Component, OnInit } from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-new.component.html',
  styleUrls: ['./rental-new.component.css']
})
export class RentalNewComponent implements OnInit {

  constructor(private rentalService: RentalService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveRental(rentalSerialNumber: string, clientID: string, movieID: string,returned:string) {
    console.log("saving rental", rentalSerialNumber, clientID, movieID,returned);

    this.rentalService.saveRental({
      id: 0,
      rentalSerialNumber,
      clientID:+clientID,
      movieID: +movieID,
      returned:false
    })
      .subscribe(rental => console.log("saved rental: ", rental));

    this.location.back(); // ...
  }

}
