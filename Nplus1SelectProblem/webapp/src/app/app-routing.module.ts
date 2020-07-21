import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {MoviesComponent} from "./movies/movies.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {ClientNewComponent} from "./clients/client-new/client-new.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {MovieNewComponent} from "./movies/movie-new/movie-new.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalDetailComponent} from "./rentals/rental-detail/rental-detail.component";
import {RentalNewComponent} from "./rentals/rental-new/rental-new.component";
import {ClientFilterComponent} from "./clients/client-filter/client-filter.component";
import {ClientPaginatedComponent} from "./clients/client-paginated/client-paginated.component";
import {ClientSortComponent} from "./clients/client-sort/client-sort.component";
import {MovieFilterComponent} from "./movies/movie-filter/movie-filter.component";
import {MovieSortComponent} from "./movies/movie-sort/movie-sort.component";
import {RentalSortComponent} from "./rentals/rental-sort/rental-sort.component";


const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'client/detail/:id', component: ClientDetailComponent},
  {path: 'client/new', component: ClientNewComponent},
  {path: 'client/filter', component: ClientFilterComponent},
  {path: 'client/paginated', component: ClientPaginatedComponent},
  {path: 'client/sorted', component: ClientSortComponent},
  {path: 'movies', component: MoviesComponent},
  {path: 'movie/detail/:id', component: MovieDetailComponent},
  {path: 'movie/new', component: MovieNewComponent},
  {path: 'movie/filter', component: MovieFilterComponent},
  //{path: 'movie/paginated', component: MoviePaginatedComponent},
  {path: 'movie/sorted', component: MovieSortComponent},
  {path: 'rentals', component: RentalsComponent},
  {path: 'rental/detail/:cid/:mid', component: RentalDetailComponent},
  {path: 'rental/new', component: RentalNewComponent},
  {path: 'rental/sorted', component: RentalSortComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
