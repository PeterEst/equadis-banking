import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsListingComponent } from './transactions-listing.component';

describe('TransactionsListingComponent', () => {
  let component: TransactionsListingComponent;
  let fixture: ComponentFixture<TransactionsListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsListingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TransactionsListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
