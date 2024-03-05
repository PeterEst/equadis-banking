package tech.peterestephan.equadisbackend.common.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import tech.peterestephan.equadisbackend.common.exceptions.NotFoundException;
import tech.peterestephan.equadisbackend.common.exceptions.RequiredFieldException;
import tech.peterestephan.equadisbackend.common.utils.ApiResponse;

@ControllerAdvice
public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    private void logException(Exception ex) {
        // You can customize the logging format and level based on your requirements
        logger.error("Exception occurred: {}", ex.getMessage(), ex);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException ex) {
        logException(ex);

        return ApiResponse.<String>builder()
                .failed(ex.getMessage(), HttpStatus.NOT_FOUND)
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ApiResponse<String>> handleRequiredFieldException(RequiredFieldException ex) {
        logException(ex);

        return ApiResponse.<String>builder()
                .failed(ex.getMessage(), HttpStatus.BAD_REQUEST)
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logException(ex);

        return ApiResponse.<String>builder()
                .failed(ex.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST)
                .build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception ex) {
        logException(ex);

        return ApiResponse.<String>builder()
                .failed(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

}
