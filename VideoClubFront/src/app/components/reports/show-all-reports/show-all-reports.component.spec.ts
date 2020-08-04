import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ShowAllReportsComponent } from "./show-all-reports.component";

describe("ShowAllReportsComponent", () => {
  let component: ShowAllReportsComponent;
  let fixture: ComponentFixture<ShowAllReportsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ShowAllReportsComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowAllReportsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
