package br.com.craftlife.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.net.URI;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail handlerNoSuchElementException(NoSuchElementException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Not Found");
        problemDetail.setType(URI.create("https://api.brunodsr.com.com/errors/not-found"));
        return problemDetail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handlerNoSuchElementException(BadCredentialsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setTitle("Unauthorized");
        problemDetail.setType(URI.create("https://api.brunodsr.com.com/errors/unauthorized"));
        return problemDetail;
    }

    @ExceptionHandler(ValidationException.class)
    ProblemDetail handleValidationException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(URI.create("https://api.brunodsr.com.com/errors/bad-request"));
        return problemDetail;
    }

    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail handleAccessDeniedException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
        problemDetail.setTitle("Forbidden");
        problemDetail.setType(URI.create("https://api.brunodsr.com.com/errors/bad-request"));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handlerException(Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.brunodsr.com.com/errors/internal-server-error"));
        return problemDetail;
    }

}
