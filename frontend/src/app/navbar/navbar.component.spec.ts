import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { NavbarComponent } from './navbar.component';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a title', () => {
    const title = fixture.debugElement.query(By.css('.navbar > h2')).nativeElement;
    expect(title.textContent.trim()).toBe('STREETWISE');
  });

  it('should have links to About and Contact', () => {
    const links = fixture.debugElement.queryAll(By.css('.navbar-actions > h4'));
    expect(links.length).toBe(2);
    expect(links[0].nativeElement.textContent.trim()).toBe('About');
    expect(links[1].nativeElement.textContent.trim()).toBe('Contact');
  });
});
