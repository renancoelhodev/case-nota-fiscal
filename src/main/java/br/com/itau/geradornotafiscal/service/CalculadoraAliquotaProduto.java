package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.enums.TipoPessoa;

import java.math.BigDecimal;
import java.util.List;

public interface CalculadoraAliquotaProduto {

    BigDecimal calculaAliquota(TipoPessoa tipoPessoa, RegimeTributacaoPJ regime, BigDecimal valorTotalItens);
}
