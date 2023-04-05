import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { GetmesomewhereComponent } from './getmesomewhere.component';
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatDividerModule} from "@angular/material/divider";
import {MatTabsModule} from "@angular/material/tabs";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {
  NgxMatDatetimePickerModule,
  NgxMatNativeDateModule,
  NgxMatTimepickerModule
} from "@angular-material-components/datetime-picker";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatSelectModule} from "@angular/material/select";
import {MatOptionModule} from "@angular/material/core";
import {MatButtonModule} from "@angular/material/button";
import {GoogleMapsModule} from "@angular/google-maps";
import {WiseMapComponent} from "../shared/wise-map/wise-map.component";

describe('GetmesomewhereComponent', () => {
  let component: GetmesomewhereComponent;
  let fixture: ComponentFixture<GetmesomewhereComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatDividerModule,
        MatTabsModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        NgxMatDatetimePickerModule,
        NgxMatTimepickerModule,
        MatDatepickerModule,
        NgxMatNativeDateModule,
        MatSelectModule,
        MatOptionModule,
        MatButtonModule,
        GoogleMapsModule
      ],
      declarations: [
        GetmesomewhereComponent,
        WiseMapComponent
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GetmesomewhereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  fit('should create', () => {
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
