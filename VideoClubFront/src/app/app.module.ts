import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { AuthenticationService } from './security/authentication-service.service';
import { CanActivateAuthGuard } from './security/can-activate-auth.guard';
import { JwtUtilsService } from './security/jwt-utils.service';
import { TokenInterceptorService } from './security/token-interceptor.service';
import { HeaderComponent } from './header/header.component';
import { MenuBarComponent } from './menu-bar/menu-bar.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { RegisterUserComponent } from './pages/register-user/register-user.component';
import { DatePipe } from '@angular/common';
import { ShowFilmsComponent } from './components/films/show-films/show-films.component';
import { SearchFilmsComponent } from './components/films/search-films/search-films.component';
import { DetailsFilmComponent } from './components/films/details-film/details-film.component';
import { RatingComponent } from './components/reviews/rating/rating.component';
import { CreateReviewComponent } from './components/reviews/create-review/create-review.component';
import { MyReviewsComponent } from './components/reviews/my-reviews/my-reviews.component';
import { OffersShowComponent } from './components/offers/offers-show/offers-show.component';
import { MessageComponent } from './pages/message/message.component';
import { AddActorComponent } from './components/actors/add-actor/add-actor.component';
import { ShowAllActorsComponent } from './components/actors/show-all-actors/show-all-actors.component';
import { UpdateArtistComponent } from './components/actors/update-artist/update-artist.component';
import { CreateFilmComponent } from './components/films/create-film/create-film.component';
import { CreateOfferComponent } from './components/offers/create-offer/create-offer.component';
import { UpdateOfferComponent } from './components/offers/update-offer/update-offer.component';
import { ShowOffersAdminComponent } from './components/offers/show-offers-admin/show-offers-admin.component';
import { ShowAllReportsComponent } from './components/reports/show-all-reports/show-all-reports.component';
import { ActionShowComponent } from './components/actions/action-show/action-show.component';
import { ActionCreateComponent } from './components/actions/action-create/action-create.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuBarComponent,
    LoginComponent,
    DashboardComponent,
    RegisterUserComponent,
    ShowFilmsComponent,
    SearchFilmsComponent,
    DetailsFilmComponent,
    RatingComponent,
    CreateReviewComponent,
    MyReviewsComponent,
    OffersShowComponent,
    MessageComponent,
    AddActorComponent,
    ShowAllActorsComponent,
    UpdateArtistComponent,
    CreateFilmComponent,
    CreateOfferComponent,
    UpdateOfferComponent,
    ShowOffersAdminComponent,
    ShowAllReportsComponent,
    ActionShowComponent,
    ActionCreateComponent
  ],
  imports: [
    NgbModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule
  ],
  providers: [
    DatePipe,
 //   AlertService,
    CanActivateAuthGuard,
    JwtUtilsService,
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
