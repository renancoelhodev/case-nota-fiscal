package br.com.itau.geradornotafiscal.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerCodigo {

    public record ErrorResponse(
            int status,
            String mensagem
    ) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handler (MethodArgumentNotValidException ex) {

        String campo = ex.getBindingResult()
                .getFieldError()
                .getField();

        ErrorResponse response = new ErrorResponse(
                400,
                String.format("O campo %s possui valor inválido.", campo)
        );

        return ResponseEntity
                .status(400)
                .body(response);

    }


}
