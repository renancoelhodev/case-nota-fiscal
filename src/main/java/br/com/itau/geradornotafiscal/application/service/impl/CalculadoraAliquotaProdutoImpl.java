package br.com.itau.geradornotafiscal.application.service.impl;
import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.domain.model.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.application.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.domain.strategy.PessoaFisicaStrategy;
import br.com.itau.geradornotafiscal.domain.strategy.PessoaJuridicaStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class CalculadoraAliquotaProdutoImpl implements CalculadoraAliquotaProduto {

    private final PessoaJuridicaStrategy pessoaJuridicaStrategy;
    private final PessoaFisicaStrategy pessoaFisicaStrategy;

    public CalculadoraAliquotaProdutoImpl(
            PessoaJuridicaStrategy pessoaJuridicaStrategy,
            PessoaFisicaStrategy pessoaFisicaStrategy) {

        this.pessoaJuridicaStrategy = pessoaJuridicaStrategy;
        this.pessoaFisicaStrategy = pessoaFisicaStrategy;
    }

    @Override
    public BigDecimal calculaAliquota(
            TipoPessoa tipoPessoa,
            RegimeTributacaoPJ regime,
            BigDecimal valorTotalItens) {

        if (tipoPessoa == TipoPessoa.JURIDICA) {
            return pessoaJuridicaStrategy.calcular(regime, valorTotalItens);
        }

        return pessoaFisicaStrategy.calcular(regime, valorTotalItens);
    }
}


