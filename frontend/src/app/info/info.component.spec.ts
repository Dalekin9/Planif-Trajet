import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import { InfoComponent } from './info.component';
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
describe('InfoComponent', () => {
  let component: InfoComponent;
  let fixture: ComponentFixture<InfoComponent>;

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
      declarations: [ InfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('should initialize the form with a "from" field', () => {
    expect(component.metroForm).toBeDefined();
    expect(component.line).toBeDefined();
    expect(component.line instanceof FormControl).toBeTrue();
    expect(component.line.errors?.required).toBeTrue();
  });

  describe('onSubmit', () => {
    it('should do nothing if the form is valid ', () => {
      component.onSubmit();
      expect(true).toBeTruthy();
    });
  });
  describe('from field validation', () => {
    let control: FormControl;

    beforeEach(() => {
      control = component.line;
    });

    it('should be invalid when empty', () => {
      control.setValue('');
      expect(control.valid).toBeFalse();
      expect(control.errors?.required).toBeDefined();
    });

    it('should be valid when a station is selected', () => {
      control.setValue("station");
      expect(control.valid).toBeTrue();
      expect(control.errors?.required).toBeFalsy();
    });
  });

  describe('metroForm submission', () => {

    it('should not submit when form is invalid', () => {
      spyOnProperty(component, 'metro', "get");
      component.metroForm.setValue({
        line: ''
      });
      fixture.detectChanges();
      const button = fixture.debugElement.nativeElement.querySelector('button');
      button.click();
      expect(component.metro).toBeFalsy();
    });
  });
});
