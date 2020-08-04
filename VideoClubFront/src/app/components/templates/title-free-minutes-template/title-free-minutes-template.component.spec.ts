import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { TitleFreeMinutesTemplateComponent } from "./title-free-minutes-template.component";

describe("TitleFreeMinutesTemplateComponent", () => {
  let component: TitleFreeMinutesTemplateComponent;
  let fixture: ComponentFixture<TitleFreeMinutesTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TitleFreeMinutesTemplateComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TitleFreeMinutesTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
