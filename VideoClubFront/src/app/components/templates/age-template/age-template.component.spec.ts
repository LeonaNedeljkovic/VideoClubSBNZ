import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgeTemplateComponent } from './age-template.component';

describe('AgeTemplateComponent', () => {
  let component: AgeTemplateComponent;
  let fixture: ComponentFixture<AgeTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgeTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgeTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
