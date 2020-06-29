import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/security/authentication-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user;

  public wrongUsernameOrPass:boolean;
  public noInput:boolean;
  public needToValidate:boolean;
  message: String = '';
  type = '';
  constructor(private authenticationService:AuthenticationService,
              private router: Router) {
    this.user = {};
    this.wrongUsernameOrPass = false;
    this.noInput=false;
    this.needToValidate = false;
   }

   ngOnInit() {
  }
  
   login():void{
    if (this.user.username !=undefined && this.user.password != undefined && this.user.username != "" && this.user.password != ""){

    
    this.authenticationService.login(this.user.username, this.user.password).subscribe(
      (loggedIn) => {
        location.reload();
      }
    ,
    (err:Error) => {
          this.message="Wrong password or username.";
          this.type = 'danger';
    });

  }else {
    this.message="Please enter username and password.";
    this.type = 'danger';
  }
  }

}
