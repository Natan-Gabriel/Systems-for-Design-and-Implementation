import { Component, OnInit } from '@angular/core';

import {Router} from "@angular/router";
import {MovieService} from "../shared/movie.service";
import {Movie} from "../shared/movie.model";

@Component({
  selector: 'app-movie-sort',
  templateUrl: './movie-sort.component.html',
  styleUrls: ['./movie-sort.component.css']
})
export class MovieSortComponent  {

  errorMessage: string;
  movies: Array<Movie>;
  allMovies: Array<Movie>;
  selectedMovie: Movie;

  constructor(private movieService: MovieService,
              private router: Router) { }

  sortMoviesAngular() {
    this.movieService.getMovies()
      .subscribe(
        allMovies => this.allMovies = allMovies,
        error => this.errorMessage = <any>error
      );
  }

  sortMovies(size:string,page:string,field:string,direction:string) {
    this.movieService.sortMovies(+size,+page,field,direction)
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }

}
