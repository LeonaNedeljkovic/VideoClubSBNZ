import { Component, OnInit, Input } from '@angular/core';


import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '../security/authentication-service.service';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterUserComponent } from '../pages/register-user/register-user.component';
import { CurrentUser } from '../model/currentUser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css' ]
})
export class HeaderComponent implements OnInit {

  loggedUser: CurrentUser;
  username: string;
  loggedIn: boolean = false;
  activePage: String;


  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private _router: Router, private modalService: NgbModal, private AuthenticationService: AuthenticationService) { }

  ngOnInit() {
    this.activePage = "home";
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
      console.log(this.loggedUser);


    if (this.loggedUser == null) {
      this.loggedIn = false;

    } else {
      this.loggedIn = true;
    }
  }

  login() {
    console.log('login called');
    const modalRef = this.modalService.open(LoginComponent);
    
  }
  register() {
    const modalRef = this.modalService.open(RegisterUserComponent);
    //modalRef.componentInstance.name = 'Login';
  }



  logout() {
    console.log("OVDE");
   
    this.AuthenticationService.logout();
    localStorage.removeItem('currentUser');
    this._router.navigate(['/dashboard']);
    location.reload();
  }

}





