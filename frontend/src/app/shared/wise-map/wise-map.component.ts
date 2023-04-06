import {Component, OnInit, ViewChild} from '@angular/core';
import {MapInfoWindow, MapMarker} from '@angular/google-maps';
import Animation = google.maps.Animation;
import {RequestsService} from "../../requests.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";


@Component({
  selector: 'app-wise-map',
  templateUrl: './wise-map.component.html',
  styleUrls: ['./wise-map.component.scss']
})
export class WiseMapComponent implements OnInit {
  private unsubscribe: Subject<void> = new Subject();
  @ViewChild(MapInfoWindow) public infoWindow: MapInfoWindow;
  public center: google.maps.LatLngLiteral = {lat: 48.8566, lng: 2.3522};
  public zoom : number = 11;
  public markerOptions: google.maps.MarkerOptions = {draggable: true, animation: Animation.DROP, title:'test'};
  public selectedMarker: MapMarker = null;

  constructor(
    private service: RequestsService,
  ) { }

  ngOnInit(): void {
    this.service.getBestStations()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe()
  }

  markerPositions: google.maps.LatLngLiteral[] = [
    {lat: 48.85955653272677, lng: 2.346411849769497},       //Chatelet
    {lat: 48.84371878914501, lng: 2.3639145948709084},      //Gare d'Austerlitz
    {lat: 48.8442498880687, lng: 2.372519782814122},        //Gare de Lyon
    {lat: 48.87560743718584, lng: 2.324122029700433},       //Saint-Lazare
    {lat: 48.84382361125447, lng: 2.323989185205003},       //Montparnasse
    {lat: 48.879510553130615, lng: 2.356768884572096},      //Gare du Nord
    {lat: 48.87627821831841, lng: 2.3577495818204395},      //Gare de l'Est
    {lat: 48.87723143067265, lng: 2.4065226763982275},      // Porte Des Lilas
    {lat: 48.87672366197325, lng: 2.393139370360497},       //Place Des Fetes
    {lat: 48.83251953251351, lng: 2.2877417091423347},      // Porte de Versailles
    {lat: 48.913739225808136, lng: 2.3807826993163266},     //Mairie d'Aubervilliers
    {lat: 48.82434428766646, lng: 2.273558168023932},       //Mairie d'Issy
    {lat: 48.88742451051807, lng: 2.325686960766736},       //La Fourche
    {lat: 48.76875224860992, lng: 2.464290193819998},       //Pointe du Lac
    {lat: 48.84812886119474, lng: 2.2582639186231943},      //Porte d'Auteuil
    {lat: 48.84067248206251, lng: 2.2283125898228056},      //Boulogne Pont de Saint-Cloud
    {lat: 48.85356350094303, lng: 2.4107036110235414},      //Porte de Montreuil
    {lat: 48.8737568325288, lng: 2.327677208279404},        //Havre-Caumartin
    {lat: 48.86231313094024, lng: 2.4414386664522225},      //Mairie de Montreuil
    {lat: 48.84888521841715, lng: 2.3959437210498136},      //Nation
    {lat: 48.83799728287381, lng: 2.2567894964207142},      //Porte de Saint-Cloud
    {lat: 48.82966919074605, lng: 2.230505217088554},       //Pont de Sèvres
    {lat: 48.83665079228612, lng: 2.278509358807},          //Balard
    {lat: 48.87974365006289, lng: 2.4162978208011863},      // Mairie Des Lilas
    {lat: 48.83967393140864, lng: 2.395573606140371},       //Daumesnil
    {lat: 48.84014763512746, lng: 2.3791909087742877}       //Bercy
  ];

  public addMarker(event: google.maps.MapMouseEvent): void {
    this.markerPositions.push(event.latLng.toJSON());
  }

  public openInfoWindow(marker: MapMarker): void {
    this.selectedMarker = marker;
    this.infoWindow.open(marker);
  }

  public deleteMarker(marker: MapMarker): void {
    marker.marker.setMap(null);
  }
}
