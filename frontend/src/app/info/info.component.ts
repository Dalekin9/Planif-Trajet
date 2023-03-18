import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit {

  public metroForm: FormGroup;
  public stationsForTest: string[] = ['Bibliothèque François ..', 'Cour Saint Emilion', 'Bercy', 'Gare de Lyon', 'Chatelet', 'Pyramides', 'Madeleine'];

  constructor() { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.metroForm = new FormGroup({
      from: new FormControl('', [Validators.required])
    });
  }

  public get from(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public onSubmit() {

  }
}
