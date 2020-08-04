import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { AgeFreeMinutesTemplateComponent } from "./age-free-minutes-template.component";

describe("AgeFreeMinutesTemplateComponent", () => {
  let component: AgeFreeMinutesTemplateComponent;
  let fixture: ComponentFixture<AgeFreeMinutesTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AgeFreeMinutesTemplateComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgeFreeMinutesTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
