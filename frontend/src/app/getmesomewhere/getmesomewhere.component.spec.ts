import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GetmesomewhereComponent } from './getmesomewhere.component';

describe('GetmesomewhereComponent', () => {
  let component: GetmesomewhereComponent;
  let fixture: ComponentFixture<GetmesomewhereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GetmesomewhereComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetmesomewhereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
