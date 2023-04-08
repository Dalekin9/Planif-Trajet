import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WiseMapComponent } from './wise-map.component';
import {GoogleMapsModule, MapInfoWindow, MapMarker} from '@angular/google-maps';
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
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

describe('WiseMapComponent', () => {
  let component: WiseMapComponent;
  let fixture: ComponentFixture<WiseMapComponent>;

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
      declarations: [ WiseMapComponent ],
      providers: [ MapInfoWindow ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WiseMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add a marker when addMarker is called', () => {
    const event = {
      latLng: {
        toJSON: () => ({ lat: 48.8566, lng: 2.3522 })
      }
    } as google.maps.MapMouseEvent;
    const lengthBefore = component.markerPositions.length;
    component.addMarker(event);
    expect(component.markerPositions.length).toBe(lengthBefore + 1);
  });

  it('should have a default center position', () => {
    expect(component.center).toEqual({lat: 48.8566, lng: 2.3522});
  });

  it('should have a default zoom level', () => {
    expect(component.zoom).toEqual(11);
  });

  it('should set the selected marker and open the info window on openInfoWindow', () => {
    const marker = {} as MapMarker;
    spyOn(component.infoWindow, 'open');
    component.openInfoWindow(marker);
    expect(component.selectedMarker).toBe(marker);
    expect(component.infoWindow.open).toHaveBeenCalledWith(marker);
  });

});
