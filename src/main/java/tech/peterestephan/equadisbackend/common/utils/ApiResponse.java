package tech.peterestephan.equadisbackend.common.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.peterestephan.equadisbackend.transaction.domain.entities.Transaction;

public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final PageMetaData pagination;

    private ApiResponse(boolean success, T data, String message, PageMetaData pagination) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.pagination = pagination;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public PageMetaData getPagination() {
        return pagination;
    }

    public static class ApiResponseBuilder<T> {
        private boolean success;
        private T data;
        private String message;
        private HttpStatus status;
        private PageMetaData pagination;

        public ApiResponseBuilder<T> pagination(Page pagination) {
            this.pagination = new PageMetaData(
                    pagination.getNumber(),
                    pagination.getSize(),
                    pagination.getTotalElements(),
                    pagination.getTotalPages()
            );

            return this;
        }

        public ApiResponseBuilder<T> success(T data, String message, HttpStatus status) {
            this.success = true;
            this.data = data;
            this.message = message;
            this.status = status;

            return this;
        }

        public ApiResponseBuilder<T> success(T data, String message) {
            this.success = true;
            this.data = data;
            this.message = message;
            this.status = HttpStatus.OK;

            return this;
        }

        public ApiResponseBuilder<T> failed(String message, HttpStatus status) {
            this.success = false;
            this.data = null;
            this.message = message;
            this.status = status;

            return this;
        }

        public ResponseEntity<ApiResponse<T>> build() {
            ApiResponse<T> response = new ApiResponse<>(success, data, message, pagination);

            return ResponseEntity.status(status).body(response);
        }
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }
}
