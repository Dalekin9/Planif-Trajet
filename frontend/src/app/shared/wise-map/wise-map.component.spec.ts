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
import {MatIconModule} from "@angular/material/icon";

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
        GoogleMapsModule,
        MatIconModule
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
    component.path = [
      {
        metroLine: "14",
        terminusStation: "Bercy",
        nodes: [
          {
            start: {
              name: "Bercy",
              longitude: 2.3791909087742877,
              latitude: 48.84014763512746
            },
            end: {
              name: "Gare de Lyon",
              longitude: 2.372519782814122,
              latitude: 48.8442498880687
            },
            weight: 67458,
            metroLine: "14",
            terminusStation: "Bercy"
          },
          {
            start: {
              name: "Gare de Lyon",
              longitude: 2.372519782814122,
              latitude: 48.8442498880687
            },
            end: {
              name: "Châtelet",
              longitude: 2.346411849769497,
              latitude: 48.85955653272677
            },
            weight: 67723,
            metroLine: "14",
            terminusStation: "Bercy"
          }
        ]
      }
    ]
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
    const lengthBefore = component.stationsMarkers.length;
    component.addMarker(event);
    expect(component.stationsMarkers.length).toBe(lengthBefore + 1);
  });

  it('should have a default center position', () => {
    expect(component.center).toEqual({lat: 48.8566, lng: 2.3522});
  });

  it('should have a default zoom level', () => {
    expect(component.zoom).toEqual(11);
  });

  it('should delete a marker', () => {
    const toBeDeletedMarker = new google.maps.Marker({title: "Custom Marker"});
    const notToBeDeletedMarker = new google.maps.Marker({title: "Custom Marker"});
    component.deleteMarker(toBeDeletedMarker);
    let map = toBeDeletedMarker.getMap();
    expect(map).toBeFalsy();
  });

  it('should open info window', () => {
    const marker1 = new MapMarker(null, null);
    marker1.marker = new google.maps.Marker({title: 'Marker 1'});
    const marker2 = new MapMarker(null, null);
    marker2.marker = new google.maps.Marker({title: 'Marker 2'})
    component.stationsMarkers = [{marker: marker1.marker, deletable: false}, {marker: marker2.marker, deletable: false}];

    spyOn(component.infoWindow, 'open');
    component.openInfoWindow(marker2);
    expect(component.selectedMarker).toBeDefined();
    expect(component.infoWindow.open).toHaveBeenCalled();
  })

  it('should emit on come from (custom point)', () => {
    const marker1 = new MapMarker(null, null);
    marker1.marker = new google.maps.Marker({title: CUSTOM_MARKER, position: new google.maps.LatLng(10, 10)});
    component.selectedMarker = {marker: marker1.marker, deletable: true};
    expect(component.selectedMarker.marker.getTitle() === marker1.marker.getTitle()).toBeTrue();
    component.onComeFrom.subscribe((value) => {
      expect(value === marker1.marker.getPosition()).toBeTrue();
    });
    component.comeFrom();
  });

  it('should emit on come from (station)', () => {
    const marker1 = new MapMarker(null, null);
    marker1.marker = new google.maps.Marker({title: 'Bercy', position: new google.maps.LatLng(10, 10)});
    component.selectedMarker = {marker: marker1.marker, deletable: true};
    expect(component.selectedMarker.marker.getTitle() === marker1.marker.getTitle()).toBeTrue();
    component.onComeFrom.subscribe((value) => {
      expect(value === marker1.marker.getTitle()).toBeTrue();
    });
    component.comeFrom();
  });

  it('should emit on go to (custom point)', () => {
    const marker1 = new MapMarker(null, null);
    marker1.marker = new google.maps.Marker({title: CUSTOM_MARKER, position: new google.maps.LatLng(10, 10)});
    component.selectedMarker = {marker: marker1.marker, deletable: true};
    expect(component.selectedMarker.marker.getTitle() === marker1.marker.getTitle()).toBeTrue();
    component.onGoTo.subscribe((value) => {
      expect(value === marker1.marker.getPosition()).toBeTrue();
    });
    component.goTo();
  });

  it('should emit on go to (station)', () => {
    const marker1 = new MapMarker(null, null);
    marker1.marker = new google.maps.Marker({title: 'Bercy', position: new google.maps.LatLng(10, 10)});
    component.selectedMarker = {marker: marker1.marker, deletable: true};
    expect(component.selectedMarker.marker.getTitle() === marker1.marker.getTitle()).toBeTrue();
    component.onGoTo.subscribe((value) => {
      expect(value === marker1.marker.getTitle()).toBeTrue();
    });
    component.goTo();
  });

  it('should set path', () => {
    expect(component.polyLines.length).toBe(1);
    expect(component.stationsMarkers.length).toBe(2);
  });

  it('should delete polyLines', () => {
    component.path = [];
    expect(component.polyLines.length).toBe(0);
    expect(component.stationsMarkers.length).toBe(0);
  });
});

const CUSTOM_MARKER = "Custom Marker";
