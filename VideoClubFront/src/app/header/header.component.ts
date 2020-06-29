import { Component, OnInit, Input } from '@angular/core';


import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { UserModel } from '../model/user.model';
import { AuthenticationService } from '../security/authentication-service.service';
import { LoginComponent } from '../pages/login/login.component';
import { RegisterUserComponent } from '../pages/register-user/register-user.component';
import { CurrentUser } from '../model/currentUser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', './general.scss']
})
export class HeaderComponent implements OnInit {

  loggedUser: CurrentUser;
  username: string;


  //moze da se user povuce iz storage-a; uloga i username, ne ceo user, jer se cuva token 
  constructor(private _router: Router, private modalService: NgbModal, private AuthenticationService: AuthenticationService) { }

  ngOnInit() {
    this.loggedUser = JSON.parse(
      localStorage.getItem('currentUser'));
      console.log(this.loggedUser);

    var login: HTMLElement = document.getElementById('loginButton');
    var notRegistrated: HTMLElement = document.getElementById('notRegistrated');
    var registrated: HTMLElement = document.getElementById('registrated');


    if (this.loggedUser == null) {
      //alert('niko nije ulogovan');
      //login.hidden = false;
      //register.hidden = false;
      //logout.hidden = true;
      //edit.hidden = true;
      console.log("SDSD");
      notRegistrated.hidden = false;
      registrated.hidden = true;
      registrated.hidden = true;

    } else {
      notRegistrated.hidden = true;
      registrated.hidden = false;
      if (this.loggedUser.userRoleName === "ROLE_ADMIN") {
        notRegistrated.hidden = true;
        login.hidden = true;
      } else {
        login.hidden = true;
      }

    }

  }

  open() {
    console.log('login called');
    const modalRef = this.modalService.open(LoginComponent);
    
  }
  openReg() {

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





