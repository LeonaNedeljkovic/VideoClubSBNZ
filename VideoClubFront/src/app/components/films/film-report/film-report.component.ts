import { Component, OnInit } from '@angular/core';
import { FinalReport } from 'src/app/dto/final-report';
import { SharedService } from 'src/app/services/shared.service';
import { NgbModal, NgbActiveModal  } from '@ng-bootstrap/ng-bootstrap';
import { FilmService } from 'src/app/services/film.service';
import { Message } from 'src/app/dto/message';
import { MessageComponent } from 'src/app/pages/message/message.component';

@Component({
  selector: 'app-film-report',
  templateUrl: './film-report.component.html',
  styleUrls: ['./film-report.component.css']
})
export class FilmReportComponent implements OnInit {

  private report : FinalReport;

  constructor(private sharedService : SharedService, private activeModal: NgbActiveModal, private filmService: FilmService,private modalService: NgbModal) { }

  ngOnInit() {
    this.report = this.sharedService.report;
    console.log(this.report);
  }

  add(){
    this.filmService.createFilm(this.sharedService.filmToAdd).subscribe(data => {
      if(data!=null){
        var message : Message = {header:"Success", message:"Film was successfully added", color:"green"};
        localStorage.setItem('message', JSON.stringify(message));
        const modalRef = this.modalService.open(MessageComponent);
        localStorage.removeItem("message2");
      }else{
        var message : Message = {header:"No success", message:"Film was not successfully added", color:"green"};
        localStorage.setItem('message', JSON.stringify(message));
        const modalRef = this.modalService.open(MessageComponent);
        localStorage.removeItem("message2");
      }
  });

  }

  close(){
    this.activeModal.dismiss();

  }

}
