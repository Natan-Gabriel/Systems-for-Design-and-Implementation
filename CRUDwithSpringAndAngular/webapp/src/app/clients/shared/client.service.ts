import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Client} from "./client.model";
import {map} from "rxjs/operators";


@Injectable()
export class ClientService {
  private clientsUrl = 'http://localhost:8080/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Client[]> {
    return this.httpClient
      .get<Array<Client>>(this.clientsUrl);
  }


  getClient(id: number): Observable<Client> {
    return this.getClients()
      .pipe(
        map(students => students.find(client => client.id === id))
      );
  }

  saveClient(client: Client): Observable<Client> {
    console.log("saveClient", client);

    return this.httpClient
      .post<Client>(this.clientsUrl, client);
  }

  update(client): Observable<Client> {
    const url = `${this.clientsUrl}/${client.id}`;
    return this.httpClient
      .put<Client>(url, client);
  }

  deleteClient(id: number): Observable<any> {
    const url = `${this.clientsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

  filterClients(filter:string,size:number,page:number): Observable<Client[]> {
    let url=`http://localhost:8080/api/clients/filter/${filter}/${size}/${page}`;
    return this.httpClient
      .get<Array<Client>>(url);
  }
  getClientsPaginated(size:string,number:string): Observable<Client[]>{
    let url=`http://localhost:8080/api/clients/${size}/${number}`;
    return this.httpClient
      .get<Array<Client>>(url);
  }
  sortClients(size:number,page:number,field:string,direction:string): Observable<Client[]>{
    let url=`http://localhost:8080/api/clients/sorted/${size}/${page}/${field}/${direction}`;
    return this.httpClient
      .get<Array<Client>>(url);
  }

}
