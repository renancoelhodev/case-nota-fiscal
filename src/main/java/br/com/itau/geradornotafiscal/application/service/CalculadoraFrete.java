package br.com.itau.geradornotafiscal.application.service;

import br.com.itau.geradornotafiscal.domain.model.Endereco;

import java.math.BigDecimal;
import java.util.List;

public interface CalculadoraFrete {

    BigDecimal calcularFrete(BigDecimal valorFrete, List<Endereco> enderecos);
}
