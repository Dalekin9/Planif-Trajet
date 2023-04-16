import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {IMetro} from "../types/dtos";
import {RequestsService} from "../requests.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

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
}