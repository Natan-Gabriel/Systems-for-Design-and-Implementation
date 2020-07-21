import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-sort',
  templateUrl: './rental-sort.component.html',
  styleUrls: ['./rental-sort.component.css']
})
export class RentalSortComponent {

  errorMessage: string;
  rentals: Array<Rental>;
  allRentals: Array<Rental>;
  selectedRental: Rental;

  constructor(private rentalService: RentalService,
              private router: Router) { }

  sortRentalsAngular() {
    this.rentalService.getRentals()
      .subscribe(
        allRentals => this.allRentals = allRentals,
        error => this.errorMessage = <any>error
      );
  }

  sortRentals(size:string,page:string,field:string,direction:string) {
    this.rentalService.sortRentals(+size,+page,field,direction)
      .subscribe(
        rentals => this.rentals = rentals,
        error => this.errorMessage = <any>error
      );
  }

}
