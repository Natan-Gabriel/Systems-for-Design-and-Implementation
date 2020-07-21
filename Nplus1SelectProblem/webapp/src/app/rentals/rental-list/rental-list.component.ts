import { Component, OnInit } from '@angular/core';
import {Rental} from "../shared/rental.model";
import {Router} from "@angular/router";
import {RentalService} from "../shared/rental.service";

@Component({
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css']
})
export class RentalListComponent implements OnInit {

  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;

  constructor(private rentalService: RentalService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getRentals();
  }

  getRentals() {
    this.rentalService.getRentals()
      .subscribe(
        rentals => this.rentals = rentals,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }

  gotoDetail(): void {
    this.router.navigate(['/rental/detail', this.selectedRental.client,this.selectedRental.movie]);
  }

  deleteRental(rental: Rental) {
    console.log("deleting rental: ", rental);

    this.rentalService.deleteRental(rental.client,rental.movie)
      .subscribe(_ => {
        console.log("rental deleted");

        this.rentals = this.rentals
          .filter(s => (s.client !== rental.client || s.movie !== rental.client ));
      });
  }

}
