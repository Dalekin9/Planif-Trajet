import {ChangeDetectorRef, Component, EventEmitter, OnInit, Output, ViewChild, ViewRef} from '@angular/core';
import {MapInfoWindow, MapMarker} from '@angular/google-maps';
import {RequestsService} from "../../requests.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {IMetroStationCorrespondence} from "../../types/dtos";

const CUSTOM_MARKER = "Custom Marker";

const DEFAULT_MAKER_ICON = {
  path: google.maps.SymbolPath.CIRCLE,
  fillOpacity: 1,
  fillColor: '#f5bd00',
  strokeOpacity: 1,
  strokeColor: '#000',
  strokeWeight: 2,
  scale: 8
};

@Component({
  selector: 'app-wise-map',
  templateUrl: './wise-map.component.html',
  styleUrls: ['./wise-map.component.scss']
})
export class WiseMapComponent implements OnInit {
  private unsubscribe: Subject<void> = new Subject();
  @ViewChild(MapInfoWindow) public infoWindow: MapInfoWindow;
  public center: google.maps.LatLngLiteral = {lat: 48.8566, lng: 2.3522};
  public zoom: number = 11;
  public stationsMarkers: IMarkerStation[] = [];
  public selectedMarker: IMarkerStation = null;
  public defaultStations: IMetroStationCorrespondence[] = [];
  @Output() onComeFrom:EventEmitter<any> = new EventEmitter();
  @Output() onGoTo:EventEmitter<any> = new EventEmitter();


  constructor(
    private service: RequestsService,
    private cdRef: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    this.service.getBestStations()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((bestStations) => {
        this.initializeMarkers(bestStations);
        this.markForCheck();
      });
  }

  public addMarker(coords: google.maps.MapMouseEvent): void {
    this.stationsMarkers.push({marker: new google.maps.Marker({position: coords.latLng, title: CUSTOM_MARKER})});
  }

  public openInfoWindow(marker: MapMarker): void {
    this.selectedMarker = this.stationsMarkers.find((stationMarker) => stationMarker.marker.getPosition() === marker.marker.getPosition());
    this.infoWindow.open(marker);
  }

  public deleteMarker(marker: google.maps.Marker): void {
    if (marker.getTitle() === CUSTOM_MARKER) {
      marker.setMap(null);
    }
  }

  public initializeMarkers(bestStations: IMetroStationCorrespondence[]) {
    this.defaultStations = bestStations;
    this.stationsMarkers = this.defaultStations.map((station) => {
      return {
        station,
        marker: new google.maps.Marker({
          position: WiseMapComponent.coordsToLatLng(station.station.latitude, station.station.longitude),
          draggable: false,
          title: station.station.name,
          icon: DEFAULT_MAKER_ICON
        })
      };
    });
  }

  private markForCheck(): void {
    if (!!this.cdRef && !(this.cdRef as ViewRef).destroyed) {
      this.cdRef.markForCheck();
    }
  }

  private static coordsToLatLng(lat: number, lon: number): google.maps.LatLng {
    return new google.maps.LatLng(lat, lon);
  }

  public comeFrom(): void{
    this.onComeFrom.emit(this.selectedMarker.marker.getPosition())
    this.infoWindow.close()
  }

  public goTo(): void{
    this.onGoTo.emit(this.selectedMarker.marker.getPosition())
    this.infoWindow.close()
  }
}

interface IMarkerStation {
  station?: IMetroStationCorrespondence,
  marker?: google.maps.Marker
}
