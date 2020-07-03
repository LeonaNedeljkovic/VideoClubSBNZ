import { Component, OnInit } from '@angular/core';
import { OfferService } from 'src/app/services/offer.service';
import { Offer } from 'src/app/model/offer.model';
import { OfferDto } from 'src/app/dto/offer-dto';
import { PurchaseService } from 'src/app/services/purchase.service';
import { Purchase } from 'src/app/model/purchase.model';
import { Router } from '@angular/router';
import { MessageComponent } from 'src/app/pages/message/message.component';
import { Message } from 'src/app/dto/message';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-offers-show',
  templateUrl: './offers-show.component.html',
  styleUrls: ['./offers-show.component.css']
})
export class OffersShowComponent implements OnInit {

  private offers : OfferDto[] = [];

  constructor(private _router: Router, private modalService: NgbModal,private offerService : OfferService, private purchaseService : PurchaseService) { }

  ngOnInit() {
    var loogedUser = JSON.parse(localStorage.getItem('currentUser'));
    if(loogedUser != null){
      this.getOffersForUser();
    }
  }

  getOffersForUser(){
    this.offerService.getOffersForUser().subscribe(
      (customOffers:OfferDto[]) => {
        this.offers = customOffers;
      }
    )
  }

  purchase(offerId:number){
    this.purchaseService.createPurchase(offerId.toString()).subscribe(
      (purchase:Purchase) => {
        
      }
    )
    var message : Message = {header:"Purchase done!", message:"You have bought minutes successfully!", color:"green"};
    localStorage.setItem('message', JSON.stringify(message));
    const modalRef = this.modalService.open(MessageComponent);
  }

}
