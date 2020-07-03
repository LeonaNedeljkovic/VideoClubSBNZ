import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterUserComponent } from './pages/register-user/register-user.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ShowFilmsComponent } from './components/films/show-films/show-films.component';
import { SearchFilmsComponent } from './components/films/search-films/search-films.component';
import { DetailsFilmComponent } from './components/films/details-film/details-film.component';
import { RatingComponent } from './components/reviews/rating/rating.component';
import { CreateReviewComponent } from './components/reviews/create-review/create-review.component';
import { MyReviewsComponent } from './components/reviews/my-reviews/my-reviews.component';
import { OffersShowComponent } from './components/offers/offers-show/offers-show.component';
import { MessageComponent } from './pages/message/message.component';




const routes: Routes = [
 {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'rating',
    component: RatingComponent,
  },
  {
    path: 'review',
    component: CreateReviewComponent,
  },
  {
    path: 'registerUser',
    component: RegisterUserComponent,
  },
  {
    path: 'message',
    component: MessageComponent,
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: 'films-show', component: ShowFilmsComponent },
      { path: 'films-search', component: SearchFilmsComponent },
      { path: 'film-details', component: DetailsFilmComponent },
      { path: 'reviews', component: MyReviewsComponent },
      { path: 'offers', component: OffersShowComponent }
    ] 
  },

  {path: '**', redirectTo: 'dashboard/films-show'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }