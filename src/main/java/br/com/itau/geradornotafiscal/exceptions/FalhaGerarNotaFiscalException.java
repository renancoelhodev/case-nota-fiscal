package br.com.itau.geradornotafiscal.exceptions;

public class FalhaGerarNotaFiscalException extends RuntimeException {

    public FalhaGerarNotaFiscalException(String message, Throwable cause) {
        super(message, cause);
    }
}