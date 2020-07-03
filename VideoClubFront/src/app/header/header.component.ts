import { Component, OnInit, Input } from '@angular/core';


import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '../security/authentication-service.service';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterUserComponent } from '../pages/register-user/register-user.component';
import { CurrentUser } from '../model/currentUser';
import { Router } from '@angular/router';
import { Notification } from '../model/notification.model';
import { NotificationService } from '../services/notification.service';
import { Message } from '../dto/message';
import { MessageComponent } from '../pages/message/message.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css' ]
})
export class HeaderComponent implements OnInit {

  loggedUser: CurrentUser;
  username: string;
  loggedIn: boolean = false;
  activePage: string;
  notifications : Notification[] = [];


  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private _router: Router, private modalService: NgbModal,
     private AuthenticationService: AuthenticationService,
     private notificationService : NotificationService) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
    if (this.loggedUser == null) {
      this.loggedIn = false;
      this.home();
    } else {
      this.loggedIn = true;
      this.reloadActiveUrl();
    }
    this.getNotifications();
  }

  getNotifications(){
    this.notificationService.getNotifications().subscribe(
      (notifications:Notification[]) => {
        this.notifications = notifications;
      }
    )
  }

  unopenedNotifications(){
    let unopned = 0;
    this.notifications.forEach(notification => {
      if(notification.opened === false){
        unopned++;
      }
    });
    return unopned;
  }

  reloadActiveUrl(){
    if(this._router.url === '/dashboard/films-show'){
      this.activePage = "home";
    }
    else if(this._router.url === '/dashboard/films-search'){
      this.activePage = "home";
    }
    else if(this._router.url === '/dashboard/reviews'){
      this.activePage = "profile";
    }
    else if(this._router.url === '/dashboard/offers'){
      this.activePage = "offers";
    }
  }

  login() {
    console.log('login called');
    const modalRef = this.modalService.open(LoginComponent);
    
  }
  register() {
    const modalRef = this.modalService.open(RegisterUserComponent);
  }

  home(){
    this.activePage = "home";
    this._router.navigate(['/dashboard/films-show']);
  }

  logout() {
    this.AuthenticationService.logout();
    localStorage.removeItem('currentUser');
    location.reload();
  }

  profile(){
    this.activePage = "profile";
    this._router.navigate(['/dashboard/reviews']);
  }

  offers(){
    this.activePage = "offers";
    this._router.navigate(['/dashboard/offers']);
  }

  openNotification(header:string, body:string, id:string, opened:boolean){
    let elId = id;
    var message : Message = {header:header, message:body, color:"green"};
    localStorage.setItem('message', JSON.stringify(message));
    const modalRef = this.modalService.open(MessageComponent);
    if(opened === false){
      this.notificationService.openNotification(id).subscribe();
      this.notifications.forEach(element => {
        if(element.id == elId){
          element.opened = true;
        }
      });
    }
  }

  addActor(){
    this.activePage = "add-actor";
    this._router.navigate(['/dashboard/add-actor']);
  }

  showAllActors(){
    this.activePage = "show-all-actors";
    this._router.navigate(['/dashboard/show-all-actors']);
  }

  createFilm(){
    this.activePage="create-film";
    this._router.navigate(['dashboard/create-film']);
  }

}





