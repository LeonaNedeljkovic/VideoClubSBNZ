import { Component, OnInit } from '@angular/core';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  private message : Message;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
    this.message = JSON.parse(localStorage.getItem('message')) as Message;
  }

  close(){
    this.modalService.dismissAll();
  }

}
