package br.com.itau.geradornotafiscal.exceptions;

public class FalhaBaixaEstoqueException extends RuntimeException {

    public FalhaBaixaEstoqueException(String message, Throwable cause) {
        super(message, cause);
    }
}
