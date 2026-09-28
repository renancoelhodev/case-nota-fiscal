package br.com.itau.geradornotafiscal.exceptions;

public class FalhaAgendamentoEntregaException extends RuntimeException {

    public FalhaAgendamentoEntregaException(String message, Throwable cause) {
        super(message, cause);
    }
}
