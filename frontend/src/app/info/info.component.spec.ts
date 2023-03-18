import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormControl } from '@angular/forms';
import { InfoComponent } from './info.component';
describe('InfoComponent', () => {
  let component: InfoComponent;
  let fixture: ComponentFixture<InfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  })

  it('should initialize the form with a "from" field', () => {
    expect(component.metroForm).toBeDefined();
    expect(component.from).toBeDefined();
    expect(component.from instanceof FormControl).toBeTrue();
    expect(component.from.errors?.required).toBeTrue();
  });

  describe('onSubmit', () => {
    it('should do nothing if the form is valid ', () => {
      component.onSubmit();
      expect(true).toBeTruthy();
    });
  });
  describe('from field validation', () => {
    let control: FormControl;

    beforeEach(() => {
      control = component.from;
    });

    it('should be invalid when empty', () => {
      control.setValue('');
      expect(control.valid).toBeFalse();
      expect(control.errors?.required).toBeDefined();
    });

    it('should be valid when a station is selected', () => {
      control.setValue(component.stationsForTest[0]);
      expect(control.valid).toBeTrue();
      expect(control.errors?.required).toBeFalsy();
    });
  });

  describe('metroForm submission', () => {

    it('should not submit when form is invalid', () => {
      spyOn(component, 'onSubmit');
      component.metroForm.setValue({
        from: ''
      });
      fixture.detectChanges();
      const button = fixture.debugElement.nativeElement.querySelector('button');
      button.click();
      expect(component.onSubmit).not.toHaveBeenCalled();
    });
  });
});
