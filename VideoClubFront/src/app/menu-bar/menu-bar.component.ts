import { Component, OnInit } from '@angular/core';
import { CurrentUser } from '../model/currentUser';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { RegisterUserComponent } from '../pages/register-user/register-user.component';

@Component({
  selector: 'app-menu-bar',
  templateUrl: './menu-bar.component.html',
  styleUrls: ['./menu-bar.component.css', './general.scss']
})
export class MenuBarComponent implements OnInit {

  currentUser : CurrentUser;
  constructor(private router: Router, private modalService: NgbModal) { }

  ngOnInit() {
    this.currentUser = JSON.parse(
      localStorage.getItem('currentUser'));
    //uzmemo sa stranice sve elemente koje treba da sakrijemo 
    // var events: HTMLElement = document.getElementById('events');
    // var locations: HTMLElement = document.getElementById('locations');
    // var myReservations: HTMLElement = document.getElementById('myReservations');
    // var reports: HTMLElement = document.getElementById('reports');
    // var addNewAdmin: HTMLElement = document.getElementById('addNewAdmin');
   
    if (this.currentUser !== null) {
      if (this.currentUser.userRoleName === 'ROLE_USER') {
      //   events.hidden = false;
      //   myReservations.hidden = false;
      //   locations.hidden = true;
      //   reports.hidden = true;
      //   addNewAdmin.hidden = true;
      
      } else if (this.currentUser.userRoleName === 'ROLE_ADMIN') {
        // events.hidden = false;
        // myReservations.hidden = true;
        // locations.hidden = false;
        // reports.hidden = false;
        // addNewAdmin.hidden = false;
      } 
    } else {
      // events.hidden = false;
      // myReservations.hidden = true;
      // locations.hidden = true;
      // reports.hidden = true;
      // addNewAdmin.hidden = true;
    }

  
  }

}
