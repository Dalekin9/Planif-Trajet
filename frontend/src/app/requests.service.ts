import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {
  IDummyData,
  IMetro,
  INode,
  IMetroStationCorrespondence,
  IDijkstraSearchResult,
  IMetroLineStationSchedules
} from "./types/dtos";

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  private readonly basicUrl: String = 'http://localhost:8080/api'

  constructor(
    private httpClient: HttpClient
  ) { }

  public getMetroList(): Observable<IMetro[]> {
    return this.httpClient.get<IMetro[]>(this.basicUrl + '/metro/list', {observe: 'body'});
  }

  public getMetroInfo(metroId: string): Observable<IMetro> {
    return this.httpClient.get<IMetro>(this.basicUrl + `/metro/${metroId}`, {observe: 'body'});
  }

  public getAllStations(): Observable<INode[]> {
    return this.httpClient.get<INode[]>(this.basicUrl + '/metro/stations', {observe: 'body'});
  }

  public getMetroLineStationSchedules(metroLine: string, station: string): Observable<IMetroLineStationSchedules> {
    let params = new HttpParams();
    params = params.append('line', metroLine);
    params = params.append('station', station);
    return this.httpClient.get<IMetroLineStationSchedules>(this.basicUrl + `/metro/station-schedules?${params.toString()}`, {observe: 'body'});
  }

  public getBestStations(): Observable<IMetroStationCorrespondence[]> {
    return this.httpClient.get<IMetroStationCorrespondence[]>(this.basicUrl + `/metro/best-stations`, {observe: 'body'});
  }

  public getStationsCorrespondence(): Observable<IMetroStationCorrespondence[]> {
    return this.httpClient.get<IMetroStationCorrespondence[]>(this.basicUrl + `/metro/stations-correspondence`, {observe: 'body'});
  }

  public getBestPath(start: string, end: string, time: number, method: string, transportation: string): Observable<IDijkstraSearchResult[]> {
    let params = new HttpParams();
    params = params.append('start', start);
    params = params.append('end', end);
    params = params.append('time', time);
    params = params.append('method', method);
    params = params.append('transportation', transportation);
    return this.httpClient.get<IDijkstraSearchResult[]>(this.basicUrl + `/path/best-path?${params.toString()}`, {observe: 'body'});
  }
}
