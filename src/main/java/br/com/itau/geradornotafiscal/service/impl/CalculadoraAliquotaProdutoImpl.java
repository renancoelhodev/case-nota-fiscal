package br.com.itau.geradornotafiscal.service.impl;
import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.enums.TipoPessoa;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.strategy.impl.PessoaFisicaStrategy;
import br.com.itau.geradornotafiscal.strategy.impl.PessoaJuridicaStrategy;
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


