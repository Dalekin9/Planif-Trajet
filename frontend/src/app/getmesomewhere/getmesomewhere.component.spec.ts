import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl } from '@angular/forms';
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

  it('should initialize the metroForm correctly', () => {
    expect(component.metroForm).toBeDefined();
    expect(component.metroForm.controls.from).toBeDefined();
    expect(component.metroForm.controls.to).toBeDefined();
    expect(component.metroForm.controls.timeToLeave).toBeDefined();
    expect(component.metroForm.controls.options).toBeDefined();
  });

  it('should set "from" field as required', () => {
    const control = component.metroForm.controls.from as FormControl;
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('Bercy');
    expect(control.valid).toBeTruthy();
  });

  it('should set "to" field as required', () => {
    const control = component.metroForm.controls.to as FormControl;
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('Gare de Lyon');
    expect(control.valid).toBeTruthy();
  });

  it('should set "timeToLeave" field as required', () => {
    const control = component.metroForm.controls.timeToLeave as FormControl;
    control.setValue(null);
    expect(control.valid).toBeFalsy();
    control.setValue(new Date());
    expect(control.valid).toBeTruthy();
  });

  it('should set "options" field as required', () => {
    const control = component.metroForm.controls.options as FormControl;
    control.setValue(null);
    expect(control.valid).toBeFalsy();
    control.setValue(['fastest']);
    expect(control.valid).toBeTruthy();
  });

  describe('onSubmit', () => {
    it('should do nothing', () => {
      component.onSubmit();
      expect(true).toBeTruthy();
    });
  });
});
