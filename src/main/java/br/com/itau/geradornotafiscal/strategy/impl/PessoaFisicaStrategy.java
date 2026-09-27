package br.com.itau.geradornotafiscal.strategy.impl;

import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.strategy.TributacaoStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PessoaFisicaStrategy implements TributacaoStrategy {

    public record AliquotaFaixaPF(BigDecimal valorTotal, BigDecimal aliquota) {}

    public List<AliquotaFaixaPF> listaFaixaPf = List.of(new AliquotaFaixaPF(new BigDecimal("500"), new BigDecimal("0")),
            new AliquotaFaixaPF(new BigDecimal("2001"), new BigDecimal("0.12")),
            new AliquotaFaixaPF(new BigDecimal("3501"), new BigDecimal("0.15")),
            new AliquotaFaixaPF(new BigDecimal("9000"), new BigDecimal("0.17")));

    @Override
    public BigDecimal calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens) {

        return listaFaixaPf.stream() .filter(f -> valorTotalItens.compareTo(f.valorTotal()) < 0)
                .findFirst()
                .map(AliquotaFaixaPF::aliquota)
                .orElseThrow();

    }
}
