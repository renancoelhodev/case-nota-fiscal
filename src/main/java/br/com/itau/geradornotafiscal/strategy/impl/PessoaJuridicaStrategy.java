package br.com.itau.geradornotafiscal.strategy.impl;

import br.com.itau.geradornotafiscal.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.strategy.TributacaoStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PessoaJuridicaStrategy implements TributacaoStrategy {

    public record AliquotaFaixa(BigDecimal valorTotal, BigDecimal aliquota) {}

    Map<RegimeTributacaoPJ, List<AliquotaFaixa>> faixas = Map.of(
            RegimeTributacaoPJ.SIMPLES_NACIONAL, List.of(
                    new AliquotaFaixa(new BigDecimal("1000"), new BigDecimal("0.03")),
                    new AliquotaFaixa(new BigDecimal("2001"), new BigDecimal("0.07")),
                    new AliquotaFaixa(new BigDecimal("5001"), new BigDecimal("0.13")),
                    new AliquotaFaixa(new BigDecimal(Integer.MAX_VALUE),  new BigDecimal("0.19"))),
            RegimeTributacaoPJ.LUCRO_REAL, List.of(
                    new AliquotaFaixa(new BigDecimal("1000"), new BigDecimal("0.03")),
                    new AliquotaFaixa(new BigDecimal("2000"), new BigDecimal("0.09")),
                    new AliquotaFaixa(new BigDecimal("5000"), new BigDecimal("0.15")),
                    new AliquotaFaixa(new BigDecimal(Integer.MAX_VALUE),  new BigDecimal("0.20"))
            ),
            RegimeTributacaoPJ.LUCRO_PRESUMIDO, List.of(
                    new AliquotaFaixa(new BigDecimal("1000"), new BigDecimal("0.03")),
                    new AliquotaFaixa(new BigDecimal("2000"), new BigDecimal("0.09")),
                    new AliquotaFaixa(new BigDecimal("5000"), new BigDecimal("0.15")),
                    new AliquotaFaixa(new BigDecimal(Integer.MAX_VALUE),  new BigDecimal("0.20"))
            )
    );

    @Override
    public BigDecimal  calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens) {

        List<AliquotaFaixa> faixas_regime = faixas.get(regime);

        return faixas_regime.stream()
                .filter(f -> valorTotalItens.compareTo(f.valorTotal()) < 0)
                .findFirst()
                .map(AliquotaFaixa::aliquota)
                .orElseThrow();
    }
}
