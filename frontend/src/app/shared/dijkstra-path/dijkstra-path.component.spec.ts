import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DijkstraPathComponent } from './dijkstra-path.component';

describe('DijkstraPathComponent', () => {
  let component: DijkstraPathComponent;
  let fixture: ComponentFixture<DijkstraPathComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DijkstraPathComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DijkstraPathComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
