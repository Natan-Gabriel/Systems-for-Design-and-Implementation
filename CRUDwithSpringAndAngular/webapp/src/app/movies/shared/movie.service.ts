import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Movie} from "./movie.model";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";



@Injectable()
export class MovieService {
  private moviesUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.httpClient
      .get<Array<Movie>>(this.moviesUrl);
  }
  getMovie(id: number): Observable<Movie> {
    return this.getMovies()
      .pipe(
        map(movies => movies.find(movie => movie.id === id))
      );
  }

  saveMovie(movie: Movie): Observable<Movie> {
    console.log("saveMovie", movie);

    return this.httpClient
      .post<Movie>(this.moviesUrl, movie);
  }

  update(movie): Observable<Movie> {
    const url = `${this.moviesUrl}/${movie.id}`;
    return this.httpClient
      .put<Movie>(url, movie);
  }

  deleteMovie(id: number): Observable<any> {
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }
  filterMovies(filter:string,size:number,page:number): Observable<Movie[]> {
    let url=`http://localhost:8080/api/movies/filter/${filter}/${size}/${page}`;
    return this.httpClient
      .get<Array<Movie>>(url);
  }
  getMoviesPaginated(size:string,number:string): Observable<Movie[]>{
    let url=`http://localhost:8080/api/movies/${size}/${number}`;
    return this.httpClient
      .get<Array<Movie>>(url);
  }
  sortMovies(size:number,page:number,field:string,direction:string): Observable<Movie[]>{
    let url=`http://localhost:8080/api/movies/sorted/${size}/${page}/${field}/${direction}`;
    return this.httpClient
      .get<Array<Movie>>(url);
  }

}
