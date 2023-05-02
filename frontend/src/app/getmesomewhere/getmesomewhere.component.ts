import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {IDijkstraPathGroup, IDijkstraSearchResult, INode} from "../types/dtos";
import {RequestsService} from "../requests.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import * as moment from 'moment';

enum Method {
  TIME = 'TIME',
  DISTANCE = 'DISTANCE'
}

enum Transportation {
  METRO = "METRO",
  METRO_FOOT = "METRO_FOOT",
  FOOT = "FOOT"
}

@Component({
  selector: 'app-getmesomewhere',
  templateUrl: './getmesomewhere.component.html',
  styleUrls: ['./getmesomewhere.component.scss']
})
export class GetmesomewhereComponent implements OnInit {
  private unsubscribe: Subject<void> = new Subject();
  private stations: INode[] = [];
  public metroForm: FormGroup;
  public filteredStationsFrom: INode[] = [];
  public filteredStationsTo: INode[] = [];
  public dijkstraPath: IDijkstraPathGroup[] = []

  constructor(
    public requestService: RequestsService
  ) {
  }

  ngOnInit(): void {
    this.initMetroForm();
    this.from.valueChanges
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(value => {
        this.filteredStationsFrom = this.filterValues(value);
      });
    this.to.valueChanges
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(value => {
        this.filteredStationsTo = this.filterValues(value);
      });
    this.requestService.getAllStations()
      .pipe(takeUntil(this.unsubscribe))
      .subscribe((stations) => {
        this.stations = stations.sort((a, b) => a.name.localeCompare(b.name));
      });
  }

  public initMetroForm(): void {
    const currentDate = new Date();
    currentDate.setSeconds(0)
    this.metroForm = new FormGroup(
      {
        from: new FormControl('', [Validators.required]),
        to: new FormControl('', [Validators.required]),
        timeToLeave: new FormControl(currentDate, [Validators.required]),
        options: new FormControl(Method.TIME, [Validators.required]),
        transportation: new FormControl(Transportation.METRO_FOOT, [Validators.required])
      }
    );
  }

  public get from(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public get to(): FormControl {
    return this.metroForm.controls?.to as FormControl;
  }

  public get timeToLeave(): FormControl {
    return this.metroForm.controls?.timeToLeave as FormControl;
  }

  public get options(): FormControl {
    return this.metroForm.controls?.options as FormControl;
  }

  public get transportation(): FormControl {
    return this.metroForm.controls?.transportation as FormControl;
  }

  public onSubmit() {
    if (this.metroForm?.valid) {
      const method = this.options.value;
      const transportation = this.transportation.value;
      const fromValue = this.from.value;
      const toValue = this.to.value;
      const time = this.timeToLeave.value.toLocaleTimeString();
      const format = 'YYYY-MM-DDThh:mm:ss A';
      const specificTime = moment(`2000-01-01T${time}`, format);
      specificTime.second(0);
      const midnight = moment('2000-01-01T12:00:00 AM', format);
      const diffInSeconds = Math.abs(specificTime.diff(midnight, 'seconds'));
      this.requestService.getBestPath(fromValue, toValue, diffInSeconds, method, transportation)
        .pipe(takeUntil(this.unsubscribe))
        .subscribe((bestPath) => {
          this.dijkstraPath = this.groupPaths(bestPath);
        });
    }
  }

  public onComeFrom(coords) {
    this.metroForm.patchValue({
      from: coords
    });
  }

  public onGoTo(coords) {
    this.metroForm.patchValue({
      to: coords
    });
  }

  public onClose(): void {
    if (!this.timeToLeave?.value) {
      return;
    }
    this.timeToLeave?.value?.setSeconds(0);
    this.timeToLeave.setValue(this.timeToLeave.value);
  }

  private filterValues(search: string): INode[] {
    return this.stations.filter(station => station.name.toLowerCase().startsWith(search.toLowerCase()));
  }

  private groupPaths(search: IDijkstraSearchResult[]): IDijkstraPathGroup[] {
    const result: IDijkstraPathGroup[] = [];
    for (let entry of search) {
      if (!!entry.metroLine) {
        if (!!result.length && result[result.length -1].metroLine === entry.metroLine) {
          result[result.length -1].nodes.push(entry);
        } else {
          result.push({metroLine: entry.metroLine, terminusStation: entry.terminusStation, nodes: [entry]});
        }
      } else {
        result.push({nodes: [entry]});
      }
    }
    return result;
  }

  public clear($event): void {
    this.from.setValue("");
    this.to.setValue("");
    this.dijkstraPath = [];
    $event.stopPropagation();
    $event.preventDefault();
  }
}
