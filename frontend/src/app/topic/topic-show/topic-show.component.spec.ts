import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicShowComponent } from './topic-show.component';

describe('TopicShowComponent', () => {
  let component: TopicShowComponent;
  let fixture: ComponentFixture<TopicShowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopicShowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopicShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});