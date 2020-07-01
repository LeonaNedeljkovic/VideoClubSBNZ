import { Component, OnInit } from '@angular/core';
import { RegisterUserService } from './register-user.service';
import { Router } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {

  public user;
  public success:boolean;
  public noInput:boolean;
  message: String = '';
  type = '';
  public repeatedPassword : String = '';
  constructor(public activeModal: NgbActiveModal, private registerUserService: RegisterUserService, private router: Router) { 
    this.user = {};
    this.success=false;
    this.noInput=false;
  }

  ngOnInit() {
  }

   validateEmail(email) 
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}
  register():void{
    this.user.enabled = false;
    if (this.user.name != undefined && this.user.surname != undefined && this.user.email != undefined &&
      this.user.username != undefined && this.user.password!=undefined && this.user.age!=undefined){
      if (this.user.password==this.repeatedPassword){
        if (this.validateEmail(this.user.email) == true){
         var e = document.getElementById("chosen");
         let indexToFind = (<HTMLSelectElement> e);
         this.user.gender= indexToFind.options[indexToFind.selectedIndex].text;
          this.registerUserService.register(this.user).subscribe(
            (registered:boolean) => {
              if(registered){
                this.message = "Successful registration, congratulations!";
                this.type = 'success';
          
              }
            }
          ,
            (err:Error) => {
                this.user.username='';
                  this.message = 'Username already exist.';
                  this.type = 'danger';
                console.log(err);
            
            });
        }else {
          this.message = 'Invalid email.';
          this.type = 'danger';
        }
        
      }else {
        this.message = 'Passwords must match.';
        this.type = 'danger';
      }
  }else {
    this.message = 'Please fill all fields.';
    this.type = 'danger';
  }
  }

}
