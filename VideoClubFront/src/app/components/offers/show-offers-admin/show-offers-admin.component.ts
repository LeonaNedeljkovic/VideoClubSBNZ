import { Component, OnInit } from '@angular/core';
import { Offer } from 'src/app/model/offer.model';
import { OfferService } from 'src/app/services/offer.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Message } from 'src/app/dto/message';
import { MessageComponent } from 'src/app/pages/message/message.component';
import { SharedService } from 'src/app/services/shared.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-offers-admin',
  templateUrl: './show-offers-admin.component.html',
  styleUrls: ['./show-offers-admin.component.css']
})
export class ShowOffersAdminComponent implements OnInit {

  private offers: Array<Offer>=[];
  private i: number = 0 ;
  constructor(private offerService: OfferService, private modelService: NgbModal, private sharedService: SharedService, private router: Router) { }

  ngOnInit() {
    this.offerService.getOffers().subscribe(data => {
      this.offers=data;
});
  }

  delete(offer){
    this.offerService.deleteOffer(offer.id).subscribe(data => {
      var message : Message = {header:data.header, message:data.message, color:"green"};
      localStorage.setItem('message', JSON.stringify(message));
      const modalRef = this.modelService.open(MessageComponent);
      
    })
    location.reload;

  }

  update(offer){
    this.sharedService.offerForUpdate=offer;
    this.router.navigate(['dashboard/update-offer']);

  }

}
