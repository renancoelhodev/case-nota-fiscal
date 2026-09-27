package br.com.itau.geradornotafiscal.strategy;

import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;

import java.math.BigDecimal;

public interface TributacaoStrategy {
    BigDecimal calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens);
}
