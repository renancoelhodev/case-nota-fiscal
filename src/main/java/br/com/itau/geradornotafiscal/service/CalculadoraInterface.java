package br.com.itau.geradornotafiscal.service;

import java.math.BigDecimal;

public interface CalculadoraInterface {


    public BigDecimal calcularAliquota(BigDecimal valor);
}
