import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  QueryList,
  ViewChild,
  ViewChildren,
  ViewRef
} from '@angular/core';
import {MapInfoWindow, MapMarker, MapPolyline} from '@angular/google-maps';
import {RequestsService} from "../../requests.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {IDijkstraPathGroup, IMetroStationCorrespondence, mapLinesColors} from "../../types/dtos";

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

interface CustomPolyline {
  vertices: google.maps.LatLngLiteral[],
  color: string,
  metroLine?: string
}

@Component({
  selector: 'app-wise-map',
  templateUrl: './wise-map.component.html',
  styleUrls: ['./wise-map.component.scss']
})
export class WiseMapComponent implements OnInit {
  private unsubscribe: Subject<void> = new Subject();
  @ViewChild(MapInfoWindow) public infoWindow: MapInfoWindow;
  @ViewChildren(MapPolyline) public mapPolyline: QueryList<MapPolyline>;
  @ViewChildren(MapMarker) public mapMarkers: QueryList<MapMarker>;
  public center: google.maps.LatLngLiteral = {lat: 48.8566, lng: 2.3522};
  public zoom: number = 11;
  public stationsMarkers: IMarkerStation[] = [];
  public selectedMarker: IMarkerStation = null;
  public defaultStations: IMetroStationCorrespondence[] = [];
  public polyLines: CustomPolyline[] = [];
  @Output() onComeFrom: EventEmitter<any> = new EventEmitter();
  @Output() onGoTo: EventEmitter<any> = new EventEmitter();

  constructor(
    private service: RequestsService,
    private cdRef: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    this.service.getBestStations()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((bestStations) => {
        this.defaultStations = bestStations;
        this.initializeMarkers();
        this.markForCheck();
      });
  }

  @Input()
  public set path(path: IDijkstraPathGroup[]) {
    this.mapPolyline?.toArray().forEach((poly: MapPolyline) => {
      poly.polyline.setMap(null);
    });
    this.mapMarkers?.toArray().forEach((marker: MapMarker) => {
      this.stationsMarkers.filter((stationMarker) => stationMarker.deletable && marker.getTitle() === stationMarker.marker.getTitle())
        .forEach(stationMarker => {
          marker.marker.setMap(null);
        });
    });
    this.initializeMarkers();
    let latLng;
    for (let group of path) {
      this.polyLines.push({
        metroLine: group.metroLine,
        color: mapLinesColors.get(group.metroLine),
        vertices: [{
          lat: group.nodes[0].start.latitude,
          lng: group.nodes[0].start.longitude
        }, {
          lat: group.nodes[group.nodes.length - 1].end.latitude,
          lng: group.nodes[group.nodes.length - 1].end.longitude
        }]
      });
      latLng = WiseMapComponent.coordsToLatLng(group.nodes[0].start.latitude, group.nodes[0].start.longitude);
      this.stationsMarkers.push({
        marker: new google.maps.Marker({
          position: latLng,
          title: group.nodes[0].start.name
        }), deletable: true
      });
    }
    if (!!path.length) {
      const lastGroup = path[path.length - 1];
      const lastNode = lastGroup.nodes[lastGroup.nodes.length - 1].end;
      latLng = WiseMapComponent.coordsToLatLng(lastNode.latitude, lastNode.longitude);
      this.stationsMarkers.push({
        marker: new google.maps.Marker({position: latLng, title: lastNode.name}),
        deletable: true
      });
    }
  }

  public addMarker(coords: google.maps.MapMouseEvent): void {
    this.stationsMarkers.push({
      marker: new google.maps.Marker({position: coords.latLng, title: CUSTOM_MARKER}),
      deletable: true
    });
  }

  public openInfoWindow(marker: MapMarker): void {
    this.selectedMarker = this.stationsMarkers.find((stationMarker) => stationMarker.marker.getPosition() === marker.marker.getPosition());
    this.infoWindow.open(marker);
  }

  public deleteMarker(marker: google.maps.Marker): void {
    const toDeleteMarker = this.stationsMarkers.find((stationMarker) => stationMarker.marker.getPosition() === marker.getPosition() && stationMarker.deletable);
    if (!!toDeleteMarker) {
      const index = this.stationsMarkers.indexOf(toDeleteMarker);
      marker.setMap(null);
      this.stationsMarkers.slice(index, 1);
    }
  }

  public initializeMarkers() {
    this.stationsMarkers = this.defaultStations.map((station) => {
      return {
        station,
        marker: new google.maps.Marker({
          position: WiseMapComponent.coordsToLatLng(station.station.latitude, station.station.longitude),
          title: station.station.name,
          icon: DEFAULT_MAKER_ICON,
          draggable: false
        }),
        deletable: false
      };
    });
    this.polyLines = [];
  }

  private markForCheck(): void {
    if (!!this.cdRef && !(this.cdRef as ViewRef).destroyed) {
      this.cdRef.markForCheck();
    }
  }

  private static coordsToLatLng(lat: number, lon: number): google.maps.LatLng {
    return new google.maps.LatLng(lat, lon);
  }

  public comeFrom(): void {
    if (this.selectedMarker.marker.getTitle() == CUSTOM_MARKER) {
      this.onComeFrom.emit(this.selectedMarker.marker.getPosition())
    } else {
      this.onComeFrom.emit(this.selectedMarker.marker.getTitle())
    }
    this.infoWindow.close()
  }

  public goTo(): void {
    if (this.selectedMarker.marker.getTitle() == CUSTOM_MARKER) {
      this.onGoTo.emit(this.selectedMarker.marker.getPosition())
    } else {
      this.onGoTo.emit(this.selectedMarker.marker.getTitle())
    }
    this.infoWindow.close()
  }
}

interface IMarkerStation {
  station?: IMetroStationCorrespondence,
  marker?: google.maps.Marker,
  deletable: boolean
}
