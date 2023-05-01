import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DijkstraPathComponent } from './dijkstra-path.component';
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
import {GoogleMapsModule} from "@angular/google-maps";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatIconModule} from "@angular/material/icon";
import {mapLinesColors} from "../../types/dtos";

describe('DijkstraPathComponent', () => {
  let component: DijkstraPathComponent;
  let fixture: ComponentFixture<DijkstraPathComponent>;

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
        MatAutocompleteModule,
        MatIconModule
      ],
      declarations: [ DijkstraPathComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DijkstraPathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    component.startingTime = '53100';
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

  it('should header click', () => {
    const spy = spyOn(component, 'onHeaderClick');
    component.onHeaderClick(null);
    expect(spy).toHaveBeenCalled();
  });

  it('should dot click', () => {
    const spy = spyOn(component, 'onDotClick');
    component.onDotClick(null);
    expect(spy).toHaveBeenCalled();
  });

  it('should expand entry', () => {
    const index = 0;
    component.onExpandEntry(true, 0);
    expect(component.expanded[index]).toBeTruthy();
    component.onExpandEntry(false, 0);
    expect(component.expanded[index]).toBeFalse();
  });

  it('should get line color', () => {
    expect(component.getLineColor("1") === mapLinesColors.get("1")).toBeTrue();
    expect(component.getLineColor("1") === mapLinesColors.get("2")).toBeFalse();
    expect(component.getLineColor("aadsfdfqsdf")).toBeFalsy();
  });

  it('should get start time for node', () => {
    expect(component.getStartTimeForNode(0) === component.startingTime).toBeTrue();
    expect(component.getStartTimeForNode(1)).toBeTruthy();
  });

  it('should get en time for node', () => {
    expect(component.getEndTimeForNode(0)).toBeTruthy();
    expect(component.getEndTimeForNode(0) === '06:48:43 PM').toBeTrue();
  });
});
