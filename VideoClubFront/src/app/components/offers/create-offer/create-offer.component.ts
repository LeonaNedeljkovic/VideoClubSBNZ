import { Component, OnInit } from "@angular/core";
import { Offer } from "src/app/model/offer.model";
import { OfferService } from "src/app/services/offer.service";
import { Message } from "src/app/dto/message";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { MessageComponent } from "src/app/pages/message/message.component";

@Component({
  selector: "app-create-offer",
  templateUrl: "./create-offer.component.html",
  styleUrls: ["./create-offer.component.css"],
})
export class CreateOfferComponent implements OnInit {
  private offer: Offer = {
    id: "",
    minutes: 0,
    price: 0,
    discounts: [],
  };

  constructor(
    private offerService: OfferService,
    private modalService: NgbModal
  ) {}

  ngOnInit() {}

  createOffer() {
    if (this.offer.minutes > 0 && this.offer.price > 0) {
      this.offerService.createOffer(this.offer).subscribe((data) => {
        if (
          data.minutes == this.offer.minutes &&
          data.price == this.offer.price
        ) {
          var message: Message = {
            header: "Offer created!",
            message: "You have created offer successfully!",
            color: "green",
          };
          localStorage.setItem("message", JSON.stringify(message));
          const modalRef = this.modalService.open(MessageComponent);
          location.reload();
        }
      });
    }
  }
}
