package br.com.itau.geradornotafiscal.domain.strategy;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;

import java.math.BigDecimal;

public interface TributacaoStrategy {
    BigDecimal calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens);
}
