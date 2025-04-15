package com.test.users.prueba_java_smartjob.exception;

import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

  @ExceptionHandler(WebExchangeBindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<ApiResponse<Void>> handleValidationExceptions(WebExchangeBindException ex) {
    return Mono.just(
        ApiResponse.error(
            ex.getFieldErrors().stream()
                .map(fieldError -> "Field "+fieldError.getField() + "missing or empty : " +
                    fieldError.getDefaultMessage())
                .collect(Collectors.joining(" | ")))
        );
  }

  @ExceptionHandler(ServerWebInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<ApiResponse<Void>> handleInputExceptions(ServerWebInputException ex) {
    return Mono.just(ApiResponse.error("Error en el formato de la solicitud: " + ex.getReason()));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Mono<ApiResponse<Void>> handleAllExceptions(Exception ex) {
    return Mono.just(ApiResponse.error("Error interno del servidor"));
  }
}
