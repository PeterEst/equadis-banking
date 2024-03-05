import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {MaterialModule} from "../../modules/shared/material/material.module";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";

@Component({
  selector: 'app-debounced-search-input',
  standalone: true,
  imports: [MaterialModule],
  templateUrl: './debounced-search-input.component.html',
  styleUrl: './debounced-search-input.component.css'
})
export class DebouncedSearchInputComponent implements OnChanges {
  @Input() debounceTime: number = 500;
  @Input() placeholder: string = "Search"
  @Output() searchChange = new EventEmitter<string>();
  private searchSubject = new Subject<string>();

  ngOnChanges(changes: SimpleChanges): void {
    this.searchSubject
      .pipe(
        debounceTime(this.debounceTime),
        distinctUntilChanged()
      )
      .subscribe(queryString => this.searchChange.emit(queryString));
  }

  onInputChange(event: any) {
    const queryString = event.target.value;
    this.searchSubject.next(queryString);
  }
}
