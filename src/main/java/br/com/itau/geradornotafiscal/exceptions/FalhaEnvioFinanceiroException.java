package br.com.itau.geradornotafiscal.exceptions;

public class FalhaEnvioFinanceiroException extends RuntimeException {

    public FalhaEnvioFinanceiroException(String message, Throwable cause) {
        super(message, cause);
    }
}