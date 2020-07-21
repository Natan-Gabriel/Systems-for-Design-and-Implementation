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

  getRental(cid: number,mid: number): Observable<Rental> {
    return this.getRentals()
      .pipe(
        map(rentals => rentals.find(rental => (rental.client === cid && rental.movie === mid)))
      );
  }

  // saveRental(cid:number,mid:number,serial:string): Observable<Object> {
  //   console.log("saveRental", cid,mid,serial);
  //
  //   return this.httpClient
  //     .post<Rental>(this.rentalsUrl, {clientId:cid,movieId:mid,serial:serial});
  // }
  saveRental(rental:Rental): Observable<Rental> {
    //console.log("saveClient", client);

    return this.httpClient
      .post<Rental>(this.rentalsUrl, rental);
  }

  update(rental): Observable<Rental> {
    const url = `${this.rentalsUrl}/${rental.client}/${rental.movie}`;
    return this.httpClient
      .put<Rental>(url, rental);
  }

  deleteRental(cid: number,mid: number): Observable<any> {
    const url = `${this.rentalsUrl}/${cid}/${mid}`;
    return this.httpClient
      .delete(url);
  }
  sortRentals(size:number,page:number,field:string,direction:string): Observable<Rental[]>{
    let url=`http://localhost:8080/api/rentals/sorted/${size}/${page}/${field}/${direction}`;
    return this.httpClient
      .get<Array<Rental>>(url);
  }

}
