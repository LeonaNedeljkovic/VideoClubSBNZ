import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/security/authentication-service.service';
import { Router } from '@angular/router';
import { MessageComponent } from '../message/message.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Message } from 'src/app/dto/message';
import { MessageResult} from 'src/app/dto/message-result.dto';

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
              private router: Router, private modalService: NgbModal) {
    this.user = {};
    this.wrongUsernameOrPass = false;
    this.noInput=false;
    this.needToValidate = false;
   }

   ngOnInit() {
  }
  
   login():void{
    if (this.user.username !=undefined && this.user.password != undefined && this.user.username != "" && this.user.password != ""){

    
    this.authenticationService.login(this.user.username, this.user.password).subscribe(data => {
      if(localStorage.getItem("message2")!=undefined){
        var message2= JSON.parse(
          localStorage.getItem('message2'));
        var messages : Message = {header:message2['message'], message:message2['result'], color:"green"};
          localStorage.setItem('message', JSON.stringify(messages));
          const modalRef = this.modalService.open(MessageComponent);
          localStorage.removeItem("message2");
      }else{
        location.reload();
      }
  });
}
   }

}
