import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {IMetro, IMetroLineStationSchedules, INode} from "../types/dtos";
import {RequestsService} from "../requests.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import * as moment from "moment";

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit, OnDestroy {
  private unsubscribe: Subject<void> = new Subject()
  private _metro: IMetro;
  public metroForm: FormGroup;
  public metros: IMetro[];
  public selectedStationSchedules: IMetroLineStationSchedules;

  constructor(
    public service: RequestsService
  ) {
  }

  ngOnInit(): void {
    this.initializeForm();
    this.service.getMetroList()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((metros: IMetro[]) => {
        this.metros = metros;
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe.complete();
  }

  private initializeForm(): void {
    this.metroForm = new FormGroup({
      line: new FormControl("", [Validators.required])
    });
  }

  public get line(): FormControl {
    return this.metroForm.controls?.line as FormControl;
  }

  public onSubmit(): void {
    if (this.metroForm.valid) {
      const metroName = this.line.value;
      this.service.getMetroInfo(metroName)
        .pipe(takeUntil(this.unsubscribe))
        .subscribe((metro: IMetro) => {
          this._metro = metro;
          this.selectedStationSchedules = null;
        });
    }
  }

  public numberToSchedule(schedule: number): string {
    const hours = Math.floor(schedule / 3600);
    const minutes = Math.round(((schedule / 3600) - hours) * 60);
    return `${hours}:${minutes.toString().padStart(2, '0')}`;
  }

  public get metro(): IMetro {
    return this._metro;
  }

  public set metro(metro: IMetro) {
    this._metro = metro;
  }

  public getSchedules(station: INode): void {
    this.service.getMetroLineStationSchedules(this._metro.name, station.name)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((result) => {
        this.selectedStationSchedules = result;
      });
  }

  public getTimeForNode(seconds: number): string {
    const midnight = moment()
    midnight.seconds(0);
    midnight.hour(0);
    midnight.minutes(0);
    midnight.add(seconds, 'seconds');
    return midnight.format('hh:mm:ss A');
  }
}
