import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {IDummyData, IMetroStationCorrespondence} from "./types/dtos";

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

  private readonly basicUrl: String = 'http://localhost:8080/api'

  constructor(
    private httpClient: HttpClient
  ) { }

  public getMetroList(): Observable<IDummyData> {
    return this.httpClient.get(this.basicUrl + '/metro/list', {observe: 'body'});
  }

  public getMetroInfo(metroId: string): Observable<IDummyData> {
    return this.httpClient.get(this.basicUrl + `/metro/${metroId}`, {observe: 'body'});
  }

  public getBestStations(): Observable<IMetroStationCorrespondence> {
    return this.httpClient.get<IMetroStationCorrespondence>(this.basicUrl + `/metro/best-stations`, {observe: 'body'});
  }

  public getBestTimePath(start: string, end: string): Observable<IDummyData> {
    let params = new HttpParams();
    params = params.append('start', start);
    params = params.append('end', end);
    return this.httpClient.get(this.basicUrl + `/path/best-time-path?${params.toString()}`, {observe: 'body'});
  }

  public getBestDistancePath(start: string, end: string): Observable<IDummyData> {
    let params = new HttpParams();
    params = params.append('start', start);
    params = params.append('end', end);
    return this.httpClient.get(this.basicUrl + `/path/best-distance-path?${params.toString()}`, {observe: 'body'});
  }

  public getBestTimeAndDistancePath(start: string, end: string): Observable<IDummyData> {
    let params = new HttpParams();
    params = params.append('start', start);
    params = params.append('end', end);
    return this.httpClient.get(this.basicUrl + `/path/best-time-distance-path?${params.toString()}`, {observe: 'body'});
  }
}
