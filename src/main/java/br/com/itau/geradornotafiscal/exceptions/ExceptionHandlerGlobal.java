package br.com.itau.geradornotafiscal.exceptions;

import br.com.itau.geradornotafiscal.domain.constant.Constantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerGlobal {

    public record ErrorResponse(
            int status,
            String mensagem
    ) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException (MethodArgumentNotValidException ex) {

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

    @ExceptionHandler(FalhaBaixaEstoqueException.class)
    public ResponseEntity<ErrorResponse> handlerFalhaBaixaEstoqueException(FalhaBaixaEstoqueException ex) {
        return ResponseEntity
                .status(502)
                .body(new ErrorResponse(502, Constantes.MENSAGEM_ERRO_BAD_GATEWAY));
    }

    @ExceptionHandler(FalhaRegistroNotaFiscalException.class)
    public ResponseEntity<ErrorResponse> handlerFalhaRegistroNotaFiscalException(FalhaRegistroNotaFiscalException ex) {
        return ResponseEntity
                .status(502)
                .body(new ErrorResponse(502, Constantes.MENSAGEM_ERRO_BAD_GATEWAY));
    }

    @ExceptionHandler(FalhaAgendamentoEntregaException.class)
    public ResponseEntity<ErrorResponse> handlerFalhaAgendamentoEntregaException(FalhaAgendamentoEntregaException ex) {
        return ResponseEntity
                .status(502)
                .body(new ErrorResponse(502, Constantes.MENSAGEM_ERRO_BAD_GATEWAY));
    }

    @ExceptionHandler(FalhaEnvioFinanceiroException.class)
    public ResponseEntity<ErrorResponse> handlerFalhaEnvioFinanceiroException(FalhaEnvioFinanceiroException ex) {
        return ResponseEntity
                .status(502)
                .body(new ErrorResponse(502, Constantes.MENSAGEM_ERRO_BAD_GATEWAY));
    }

    //pode ser outro erro
    @ExceptionHandler(FalhaGerarNotaFiscalException.class)
    public ResponseEntity<ErrorResponse> handler(FalhaGerarNotaFiscalException ex) {
        return ResponseEntity
                .status(502)
                .body(new ErrorResponse(502, Constantes.MENSAGEM_ERRO_BAD_GATEWAY));
    }
}
