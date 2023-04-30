import {ComponentFixture, TestBed} from '@angular/core/testing';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {InfoComponent} from './info.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDividerModule} from '@angular/material/divider';
import {MatTabsModule} from '@angular/material/tabs';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {
  NgxMatDatetimePickerModule,
  NgxMatNativeDateModule,
  NgxMatTimepickerModule
} from '@angular-material-components/datetime-picker';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatSelectModule} from '@angular/material/select';
import {MatOptionModule} from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';
import {GoogleMapsModule} from '@angular/google-maps';
import {MatIconModule} from "@angular/material/icon";
import {of} from "rxjs";

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
        GoogleMapsModule,
        MatIconModule
      ],
      declarations: [InfoComponent]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  afterEach(() => {
    fixture.destroy();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with a "from" field', () => {
    expect(component.metroForm).toBeDefined();
    expect(component.line).toBeDefined();
    expect(component.line instanceof FormControl).toBeTrue();
    expect(component.line.errors?.required).toBeTrue();
  });

  describe('onSubmit', () => {
    it('should call the service method to get metro info if the form is valid', () => {
      spyOn(component.service, 'getMetroInfo');
      component.line.setValue('1');
      expect(component.metroForm.valid).toBeTruthy();
    });

    it('should do nothing if the form is not valid ', () => {
      component.onSubmit();
      expect(true).toBeTruthy();
    });

    it('should not call the service method to get metro info if the form is invalid', () => {
      spyOn(component.service, 'getMetroInfo').and.callThrough();
      component.line.setValue('');
      component.onSubmit();
      expect(component.service.getMetroInfo).not.toHaveBeenCalled();
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
      control.setValue('station');
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

  describe('should transform seconds to schedule', () => {
    it('should transform seconds to schedule', () => {
      const value = component.numberToSchedule(36000);
      expect(value).toBe('10:00');
    });
  });

  it('should return a formatted string when calling numberToSchedule', () => {
    expect(component.numberToSchedule(3600)).toEqual('1:00');
    expect(component.numberToSchedule(7200)).toEqual('2:00');
    expect(component.numberToSchedule(3660)).toEqual('1:01');
  });

  it('should set "line" field as required', () => {
    const control = component.metroForm.controls.line as FormControl;
    control.setValue('');
    expect(control.valid).toBeFalsy();
    control.setValue('6');
    expect(control.valid).toBeTruthy();
  });

  it('should return a FormControl when controls exist', () => {
    const control = new FormControl('', Validators.required);
    component.metroForm.controls = {line: control};
    expect(component.line).toBe(control);
  });

  it('should return undefined when controls are undefined', () => {
    component.metroForm.controls = undefined;
    expect(component.line).toBeUndefined();
  })

  it('should get metro line station schedules', () => {
    spyOn(component.service, 'getMetroLineStationSchedules').and.returnValue(of({station: 'Bercy', metroLine: '6', schedules: []}));
    const object = {name: 'Bercy', longitude: 0, latitude: 0};
    component.metro = {name: '6'};
    component.getSchedules(object);
    expect(component.selectedStationSchedules).toBeTruthy();
  });

  it('should transform seconds to time', () => {
    expect(component.getTimeForNode(53100) === '02:45:00 PM').toBeTrue();
  });
});
