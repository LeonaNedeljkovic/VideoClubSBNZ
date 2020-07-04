import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImmunityTemplateComponent } from './immunity-template.component';

describe('ImmunityTemplateComponent', () => {
  let component: ImmunityTemplateComponent;
  let fixture: ComponentFixture<ImmunityTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImmunityTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImmunityTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
