import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomersListingComponent } from './customers-listing.component';

describe('CustomersListingComponent', () => {
  let component: CustomersListingComponent;
  let fixture: ComponentFixture<CustomersListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomersListingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CustomersListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
