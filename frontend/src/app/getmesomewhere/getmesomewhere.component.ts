import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-getmesomewhere',
  templateUrl: './getmesomewhere.component.html',
  styleUrls: ['./getmesomewhere.component.scss']
})
export class GetmesomewhereComponent implements OnInit {

  public metroForm: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.initMetroForm();
  }

  private initMetroForm(): void {
    this.metroForm = new FormGroup(
      {
        from: new FormControl('', [Validators.required]),
        to: new FormControl('', [Validators.required]),
        timeToLeave: new FormControl(null, [Validators.required]),
        options: new FormControl(null, [Validators.required])
      }
    );
  }

  public get from(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public get to(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public get timeToLeave(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public get options(): FormControl {
    return this.metroForm.controls?.from as FormControl;
  }

  public onSubmit() {

  }
}
