import {BrowserModule} from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientsComponent } from './clients/clients.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { MoviesComponent } from './movies/movies.component';
import { MovieListComponent } from './movies/movie-list/movie-list.component';
import {MovieService} from "./movies/shared/movie.service";
import { ClientDetailComponent } from './clients/client-detail/client-detail.component';
import { ClientNewComponent } from './clients/client-new/client-new.component';
import { MovieDetailComponent } from './movies/movie-detail/movie-detail.component';
import { MovieNewComponent } from './movies/movie-new/movie-new.component';
import { RentalsComponent } from './rentals/rentals.component';
import {RentalService} from "./rentals/shared/rental.service";
import { RentalListComponent } from './rentals/rental-list/rental-list.component';
import { RentalDetailComponent } from './rentals/rental-detail/rental-detail.component';
import { RentalNewComponent } from './rentals/rental-new/rental-new.component';
import { ClientSortComponent } from './clients/client-sort/client-sort.component';
import { ClientFilterComponent } from './clients/client-filter/client-filter.component';
import { ClientPaginatedComponent } from './clients/client-paginated/client-paginated.component';
import { FilterByNamePipe } from './filter-by-name.pipe';
import {ClientFilterPipe} from "./clients/client-filter.pipe";
import {ClientSortPipe} from "./clients/client-sort.pipe";
import { MovieFilterComponent } from './movies/movie-filter/movie-filter.component';
import {MovieSortPipe} from "./movies/movie-sort.pipe";
import {MovieFilterPipe} from "./movies/movie-filter.pipe";
import { MovieSortComponent } from './movies/movie-sort/movie-sort.component';
import { RentalSortComponent } from './rentals/rental-sort/rental-sort.component';
import {RentalSortPipe} from "./rentals/rental-sort.pipe";


@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    MoviesComponent,
    MovieListComponent,
    ClientDetailComponent,
    ClientNewComponent,
    MovieDetailComponent,
    MovieNewComponent,
    RentalsComponent,
    RentalListComponent,
    RentalDetailComponent,
    RentalNewComponent,
    ClientSortComponent,
    ClientFilterComponent,
    ClientPaginatedComponent,
    FilterByNamePipe,
    ClientFilterPipe,
    ClientFilterPipe,
    ClientSortPipe,
    MovieFilterComponent,
    MovieFilterPipe,
    MovieSortPipe,
    MovieFilterComponent,
    MovieSortComponent,
    RentalSortComponent,
    RentalSortPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ClientService,MovieService,RentalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
