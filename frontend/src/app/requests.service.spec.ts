import { TestBed } from '@angular/core/testing';

import { RequestsService } from './requests.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('RequestsService', () => {
  let service: RequestsService;
  let httpMock: HttpTestingController;

  const basicUrl: string = 'http://localhost:8080/api';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(RequestsService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get the list of metro stations', () => {
    service.getMetroList().subscribe(data => {
      expect(data.length).toBeGreaterThan(0)
    });
    const req = httpMock.expectOne(`${basicUrl}/metro/list`);
    expect(req.request.method).toBe('GET');
  });

  it('should get the info for a metro station', () => {
    const metroId = '1';
    service.getMetroInfo(metroId).subscribe(data => {
      expect(data).toBeDefined()
    });
    const req = httpMock.expectOne(`${basicUrl}/metro/${metroId}`);
    expect(req.request.method).toBe('GET');
  });

  it('should get the best time path', () => {
    const dummyData = { /* dummy data object */ };
    const start = 'station1';
    const end = 'station2';
    const method = 'TIME';
    const transportation = 'METRO';
    const time = 53100;
    service.getBestPath(start, end, time, method, transportation).subscribe(data => {
      expect(data).toEqual(dummyData);
    });
    const req = httpMock.expectOne(`${basicUrl}/path/best-time-path?start=${start}&end=${end}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('should get the best distance path', () => {
    const dummyData = { /* dummy data object */ };
    const start = 'station1';
    const end = 'station2';
    const method = 'DISTANCE';
    const transportation = 'METRO';
    const time = 53100;
    service.getBestPath(start, end, time, method, transportation).subscribe(data => {
      expect(data).toEqual(dummyData);
    });
    const req = httpMock.expectOne(`${basicUrl}/path/best-distance-path?start=${start}&end=${end}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('should get the best time and distance path', () => {
    const dummyData = { /* dummy data object */ };
    const start = 'station1';
    const end = 'station2';
    const method = 'TIME_DISTANCE';
    const time = 53100;
    const transportation = 'METRO';
    service.getBestPath(start, end, time, method, transportation).subscribe(data => {
      expect(data).toEqual(dummyData);
    });
    const req = httpMock.expectOne(`${basicUrl}/path/best-time-distance-path?start=${start}&end=${end}`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyData);
  });

  it('should get the best 5 stations in the network', () => {
    service.getBestStations().subscribe(data => {
      expect(data).toBeTruthy();
      expect(data.length).toEqual(5);
    });
    const req = httpMock.expectOne(`${basicUrl}/metro/best-stations`);
    expect(req.request.method).toBe('GET');
  });

  it('should get all stations correspondences in the network', () => {
    service.getStationsCorrespondence().subscribe(data => {
      expect(data).toBeTruthy();
    });
    const req = httpMock.expectOne(`${basicUrl}/metro/stations-correspondence`);
    expect(req.request.method).toBe('GET');
  });
});
