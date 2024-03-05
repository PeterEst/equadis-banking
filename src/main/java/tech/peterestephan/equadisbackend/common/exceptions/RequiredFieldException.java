package tech.peterestephan.equadisbackend.common.exceptions;

public class RequiredFieldException extends RuntimeException{
    public RequiredFieldException(String message) {
        super(message);
    }
}
