import { Component, OnInit, Input } from "@angular/core";

import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { AuthenticationService } from "../security/authentication-service.service";
import { LoginComponent } from "../pages/login/login.component";
import { RegisterUserComponent } from "../pages/register-user/register-user.component";
import { CurrentUser } from "../model/currentUser";
import { Router } from "@angular/router";
import { Notification } from "../model/notification.model";
import { NotificationService } from "../services/notification.service";
import { Message } from "../dto/message";
import { MessageComponent } from "../pages/message/message.component";
import { CreateOfferComponent } from "../components/offers/create-offer/create-offer.component";
import { AddActorComponent } from "../components/actors/add-actor/add-actor.component";
import { AgeFreeMinutesTemplateComponent } from "../components/templates/age-free-minutes-template/age-free-minutes-template.component";
import { TitleFreeMinutesTemplateComponent } from "../components/templates/title-free-minutes-template/title-free-minutes-template.component";
import { AgeTitleFreeMinutesTemplateComponent } from "../components/templates/age-title-free-minutes-template/age-title-free-minutes-template.component";
import { TitleTemplateComponent } from "../components/templates/title-template/title-template.component";
import { AgeTemplateComponent } from "../components/templates/age-template/age-template.component";
import { ImmunityTemplateComponent } from "../components/templates/immunity-template/immunity-template.component";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"],
})
export class HeaderComponent implements OnInit {
  loggedUser: CurrentUser;
  username: string;
  loggedIn: boolean = false;
  activePage: string;
  notifications: Notification[] = [];

  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token
  constructor(
    private _router: Router,
    private modalService: NgbModal,
    private AuthenticationService: AuthenticationService,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.loggedUser = JSON.parse(localStorage.getItem("currentUser"));
    if (this.loggedUser == null) {
      this.loggedIn = false;
      this.home();
    } else {
      this.loggedIn = true;
      this.reloadActiveUrl();
    }
    this.getNotifications();
  }

  getNotifications() {
    this.notificationService
      .getNotifications()
      .subscribe((notifications: Notification[]) => {
        this.notifications = notifications;
      });
  }

  unopenedNotifications() {
    let unopned = 0;
    this.notifications.forEach((notification) => {
      if (notification.opened === false) {
        unopned++;
      }
    });
    return unopned;
  }

  reloadActiveUrl() {
    if (
      this._router.url === "/dashboard/films-show" ||
      this._router.url === "/dashboard/create-film"
    ) {
      this.activePage = "home";
    } else if (this._router.url === "/dashboard/reviews") {
      this.activePage = "profile";
    } else if (
      this._router.url === "/dashboard/offers" ||
      this._router.url === "/dashboard/show-all-offers"
    ) {
      this.activePage = "offers";
    } else if (
      this._router.url === "/dashboard/action-show" ||
      this._router.url === "/dashboard/action-create"
    ) {
      this.activePage = "actions";
    } else if (this._router.url === "/dashboard/reports") {
      this.activePage = "report";
    } else if (this._router.url === "/dashboard/show-all-actors") {
      this.activePage = "artists";
    }
  }

  login() {
    console.log("login called");
    const modalRef = this.modalService.open(LoginComponent);
  }
  register() {
    const modalRef = this.modalService.open(RegisterUserComponent);
  }

  home() {
    this.activePage = "home";
    this._router.navigate(["/dashboard/films-show"]);
  }

  logout() {
    this.AuthenticationService.logout();
    localStorage.removeItem("currentUser");
    location.reload();
  }

  profile() {
    this.activePage = "profile";
    this._router.navigate(["/dashboard/reviews"]);
  }

  offers() {
    this.activePage = "offers";
    this._router.navigate(["/dashboard/offers"]);
  }

  openNotification(header: string, body: string, id: string, opened: boolean) {
    let elId = id;
    var message: Message = { header: header, message: body, color: "green" };
    localStorage.setItem("message", JSON.stringify(message));
    const modalRef = this.modalService.open(MessageComponent);
    if (opened === false) {
      this.notificationService.openNotification(id).subscribe();
      this.notifications.forEach((element) => {
        if (element.id == elId) {
          element.opened = true;
        }
      });
    }
  }

  showActions() {
    this.activePage = "actions";
    this._router.navigate(["/dashboard/action-show"]);
  }

  addActions() {
    this.activePage = "actions";
    this._router.navigate(["/dashboard/action-create"]);
  }

  addActor() {
    this.activePage = "artists";
    const modalRef = this.modalService.open(AddActorComponent);
  }

  showAllActors() {
    this.activePage = "artists";
    this._router.navigate(["/dashboard/show-all-actors"]);
  }

  createFilm() {
    this.activePage = "home";
    this._router.navigate(["/dashboard/create-film"]);
  }

  createOffer() {
    this.activePage = "offers";
    const modalRef = this.modalService.open(CreateOfferComponent);
  }

  showAllOffers() {
    this.activePage = "offers";
    this._router.navigate(["dashboard/show-all-offers"]);
  }

  report() {
    this.activePage = "report";
    this._router.navigate(["dashboard/reports"]);
  }

  freeMinutesByAge() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(AgeFreeMinutesTemplateComponent);
  }

  freeMinutesByTitle() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(TitleFreeMinutesTemplateComponent);
  }

  freeMinutesByAgeAndTitle() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(
      AgeTitleFreeMinutesTemplateComponent
    );
  }

  titleTemplaye() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(TitleTemplateComponent);
  }

  immunityTemplate() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(ImmunityTemplateComponent);
  }

  ageSettings() {
    this.activePage = "templates";
    const modalRef = this.modalService.open(AgeTemplateComponent);
  }
}
