package com.tiktak.expertiseservice.advice;

import com.tiktak.expertiseservice.exception.CarNotFoundException;
import com.tiktak.expertiseservice.exception.ExpertiseSessionNotFoundException;
import com.tiktak.expertiseservice.exception.InvalidExpertiseResponseException;
import com.tiktak.expertiseservice.exception.QuestionNotFoundException;
import com.tiktak.expertiseservice.model.dto.ErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public final class BaseControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessages);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessages);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCarNotFoundException(CarNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Car not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ExpertiseSessionNotFoundException.class)
    public ResponseEntity<ErrorDto> handleExpertiseNotFoundException(ExpertiseSessionNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Expertise session not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorDto> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.value(), "Question not found", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(InvalidExpertiseResponseException.class)
    public ResponseEntity<ErrorDto> handleInvalidExpertiseResponseException(InvalidExpertiseResponseException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), "Invalid Expertise Response", List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
