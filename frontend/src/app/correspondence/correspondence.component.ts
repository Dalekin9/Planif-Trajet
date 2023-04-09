import {Component, OnDestroy, OnInit} from '@angular/core';
import {RequestsService} from '../requests.service';
import {IMetroStationCorrespondence} from "../types/dtos";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-correspondence',
  templateUrl: './correspondence.component.html',
  styleUrls: ['./correspondence.component.scss']
})
export class CorrespondenceComponent implements OnInit, OnDestroy {
  private unsubscribe: Subject<void> = new Subject();
  public stationsCorrespondences: IMetroStationCorrespondence[] = []

  constructor(
    private service: RequestsService
  ) {
  }

  ngOnInit(): void {
    this.service.getStationsCorrespondence()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((stationsCorrespondences) => this.stationsCorrespondences = stationsCorrespondences)
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }
}
