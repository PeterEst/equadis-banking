export interface Pageable {
  page: number;
  size: number;
}

export interface Pagination extends Pageable {
  totalElements: number;
  totalPages: number;
}
