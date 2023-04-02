import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WiseMapComponent } from './wise-map.component';
import { MapInfoWindow, MapMarker } from '@angular/google-maps';

describe('WiseMapComponent', () => {
  let component: WiseMapComponent;
  let fixture: ComponentFixture<WiseMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
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

});
