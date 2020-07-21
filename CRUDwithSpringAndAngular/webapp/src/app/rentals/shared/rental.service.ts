import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Rental} from "./rental.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Client} from "../../clients/shared/client.model";


@Injectable()
export class RentalService {
  private rentalsUrl = 'http://localhost:8080/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rental[]> {
    return this.httpClient
      .get<Array<Rental>>(this.rentalsUrl);
  }

  getRental(id: number): Observable<Rental> {
    return this.getRentals()
      .pipe(
        map(rentals => rentals.find(rental => rental.id === id))
      );
  }

  saveRental(rental: Rental): Observable<Rental> {
    console.log("saveRental", rental);

    return this.httpClient
      .post<Rental>(this.rentalsUrl, rental);
  }

  update(rental): Observable<Rental> {
    const url = `${this.rentalsUrl}/${rental.id}`;
    return this.httpClient
      .put<Rental>(url, rental);
  }

  deleteRental(id: number): Observable<any> {
    const url = `${this.rentalsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }
  sortRentals(size:number,page:number,field:string,direction:string): Observable<Rental[]>{
    let url=`http://localhost:8080/api/rentals/sorted/${size}/${page}/${field}/${direction}`;
    return this.httpClient
      .get<Array<Rental>>(url);
  }

}
