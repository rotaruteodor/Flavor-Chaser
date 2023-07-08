package teodor.flavor_chaser_spring_backend.handlers;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teodor.flavor_chaser_spring_backend.dtos.ApiExceptionDto;
import teodor.flavor_chaser_spring_backend.exceptions.ResourceNotFoundException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class ApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> resourceNotFoundException(final ResourceNotFoundException exception) {
//        log.warn("{} with status {}, error code {}, message '{}' and cause {}",
//                exception.getClass().getSimpleName(), exception.getStatus(), exception.getErrorCode(),
//                exception.getMessage(), exception.getCause());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiExceptionDto.builder().code("NOT FOUND").message(exception.getMessage()).build());
    }
}
