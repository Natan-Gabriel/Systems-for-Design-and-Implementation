import { Component, OnInit } from '@angular/core';
import {Movie} from "../../movies/shared/movie.model";
import {MovieService} from "../../movies/shared/movie.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-movie-filter',
  templateUrl: './movie-filter.component.html',
  styleUrls: ['./movie-filter.component.css']
})
export class MovieFilterComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;
  allMovies: Array<Movie>;
  filteredItems: Array<Movie>;
  filteredAllMovies: Array<Movie>;
  selectedMovie: Movie;


  constructor(private movieService: MovieService,
              private router: Router) { }

  ngOnInit(): void {
    this.getAllMovies();
  }

  getAllMovies() {
    this.movieService.getMovies()
      .subscribe(
        allMovies => this.allMovies = allMovies,
        error => this.errorMessage = <any>error
      );
  }


  filterMovies(filter:string,size:string,page:string) {
    this.movieService.filterMovies(filter,+size,+page)
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }




}
