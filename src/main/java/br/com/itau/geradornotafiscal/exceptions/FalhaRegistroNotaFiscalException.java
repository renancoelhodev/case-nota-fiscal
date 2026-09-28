package br.com.itau.geradornotafiscal.exceptions;

public class FalhaRegistroNotaFiscalException extends RuntimeException {

    public FalhaRegistroNotaFiscalException(String message, Throwable cause) {
        super(message, cause);
    }
}