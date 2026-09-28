package br.com.itau.geradornotafiscal.application.service;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.domain.model.enums.TipoPessoa;

import java.math.BigDecimal;

public interface CalculadoraAliquotaProduto {

    BigDecimal calculaAliquota(TipoPessoa tipoPessoa, RegimeTributacaoPJ regime, BigDecimal valorTotalItens);
}
