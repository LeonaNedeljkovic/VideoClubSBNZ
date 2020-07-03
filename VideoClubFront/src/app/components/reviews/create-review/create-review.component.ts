import { Component, OnInit } from '@angular/core';
import { ReviewDto } from 'src/app/dto/review.dto';
import { ReviewService } from 'src/app/services/review.service';
import { Review } from 'src/app/model/review.model';

@Component({
  selector: 'app-create-review',
  templateUrl: './create-review.component.html',
  styleUrls: ['./create-review.component.css']
})
export class CreateReviewComponent implements OnInit {

  private review :ReviewDto;
  private successMessage : String = "";
  private failureMessage : String = "";

  constructor(private reviewService : ReviewService) { }

  ngOnInit() {
    console.log(localStorage.getItem('film-review'));
    this.review = {id:"0", startMinute:0, endMinute:0, videoContentId:+(localStorage.getItem('film-review'))};
  }

  watch(){
    this.reviewService.saveReview(this.review).subscribe(
      (response) => {
        if(response.result != null){
          this.failureMessage = response.result;
          this.successMessage = "";
        }
        else{
          this.failureMessage = "";
          this.successMessage = "Film watched successfully!";
        }
      }
    )
  }

}
