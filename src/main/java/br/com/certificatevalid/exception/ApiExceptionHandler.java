package br.com.certificatevalid.exception;

import br.com.certificatevalid.dto.ExceptionArgumentNotValidDto;
import br.com.certificatevalid.dto.ExceptionDto;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static br.com.certificatevalid.util.Constants.INVALID_FIELDS;
import static br.com.certificatevalid.util.Constants.MALFORMED_JSON;
import static java.time.OffsetDateTime.now;
import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> notFoundException(NotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(
                ExceptionDto.builder().status(NOT_FOUND).message(exception.getMessage()).timestamp(now()).build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> unauthorizedException(UnauthorizedException exception){
        return ResponseEntity.status(UNAUTHORIZED).body(
                ExceptionDto.builder().status(UNAUTHORIZED).message(exception.getMessage()).timestamp(now()).build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> badRequestException(BadRequestException exception){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionDto.builder().status(BAD_REQUEST).message(exception.getMessage()).timestamp(now()).build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        return new ResponseEntity(ExceptionDto.builder()
                .status(status)
                .timestamp(now())
                .message(MALFORMED_JSON)
                .build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers, @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        List<ExceptionArgumentNotValidDto.Warnings> warnings = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            warnings.add(new ExceptionArgumentNotValidDto.Warnings(name, message));
        }

        ExceptionArgumentNotValidDto exception = ExceptionArgumentNotValidDto.builder()
                .status(status)
                .timestamp(now())
                .message(INVALID_FIELDS)
                .warnings(warnings)
                .build();
        return handleExceptionInternal(ex, exception, headers, status, request);
    }

}