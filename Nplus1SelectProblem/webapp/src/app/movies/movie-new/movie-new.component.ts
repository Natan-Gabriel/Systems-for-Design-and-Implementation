import { Component, OnInit } from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-new.component.html',
  styleUrls: ['./movie-new.component.css']
})
export class MovieNewComponent implements OnInit {

  constructor(private movieService: MovieService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveMovie(serialNumber: string, name: string, year: string,rating:string) {
    console.log("saving movie", serialNumber, name, year,rating);

    this.movieService.saveMovie({
      id: 0,
      serialNumber,
      name,
      year:+year,
      rating:+rating
    })
      .subscribe(movie => console.log("saved movie: ", movie));

    this.location.back(); // ...
  }

}
