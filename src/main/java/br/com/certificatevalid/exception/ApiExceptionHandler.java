package br.com.certificatevalid.exception;

import br.com.certificatevalid.dto.ExceptionDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> NotFoundException(NotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(HttpStatus.NOT_FOUND, e.getMessage(), OffsetDateTime.now())
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDto> ForbiddenException(ForbiddenException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionDto(HttpStatus.FORBIDDEN, e.getMessage(), OffsetDateTime.now())
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> BadRequestException(BadRequestException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage(), OffsetDateTime.now())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> IllegalArgumentException(IllegalArgumentException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage(), OffsetDateTime.now())
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDto> DataIntegrityViolationException(DataIntegrityViolationException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage(), OffsetDateTime.now())
        );
    }

}