import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ShowOffersAdminComponent } from "./show-offers-admin.component";

describe("ShowOffersAdminComponent", () => {
  let component: ShowOffersAdminComponent;
  let fixture: ComponentFixture<ShowOffersAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ShowOffersAdminComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowOffersAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
