package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.model.Endereco;

import java.math.BigDecimal;
import java.util.List;

public interface CalculadoraFrete {

    BigDecimal calcularFrete(BigDecimal valorFrete, List<Endereco> enderecos);
}
