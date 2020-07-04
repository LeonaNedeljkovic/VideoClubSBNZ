import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgeTitleFreeMinutesTemplateComponent } from './age-title-free-minutes-template.component';

describe('AgeTitleFreeMinutesTemplateComponent', () => {
  let component: AgeTitleFreeMinutesTemplateComponent;
  let fixture: ComponentFixture<AgeTitleFreeMinutesTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgeTitleFreeMinutesTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgeTitleFreeMinutesTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
