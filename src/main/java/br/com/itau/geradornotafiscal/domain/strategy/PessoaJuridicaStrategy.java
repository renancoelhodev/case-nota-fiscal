package br.com.itau.geradornotafiscal.domain.strategy;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PessoaJuridicaStrategy implements TributacaoStrategy {

    public record AliquotaFaixa(
            BigDecimal limiteMinimo,
            BigDecimal limiteMaximo,
            BigDecimal aliquota
    ) {}

    private final Map<RegimeTributacaoPJ, List<AliquotaFaixa>> faixas = Map.of(

            RegimeTributacaoPJ.SIMPLES_NACIONAL, List.of(
                    new AliquotaFaixa(
                            BigDecimal.ZERO,
                            new BigDecimal("1000"),
                            new BigDecimal("0.03")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("1000"),
                            new BigDecimal("2000"),
                            new BigDecimal("0.07")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("2000"),
                            new BigDecimal("5000"),
                            new BigDecimal("0.13")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("5000"),
                            null,
                            new BigDecimal("0.19")
                    )
            ),

            RegimeTributacaoPJ.LUCRO_REAL, List.of(
                    new AliquotaFaixa(
                            BigDecimal.ZERO,
                            new BigDecimal("1000"),
                            new BigDecimal("0.03")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("1000"),
                            new BigDecimal("2000"),
                            new BigDecimal("0.09")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("2000"),
                            new BigDecimal("5000"),
                            new BigDecimal("0.15")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("5000"),
                            null,
                            new BigDecimal("0.20")
                    )
            ),

            RegimeTributacaoPJ.LUCRO_PRESUMIDO, List.of(
                    new AliquotaFaixa(
                            BigDecimal.ZERO,
                            new BigDecimal("1000"),
                            new BigDecimal("0.03")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("1000"),
                            new BigDecimal("2000"),
                            new BigDecimal("0.09")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("2000"),
                            new BigDecimal("5000"),
                            new BigDecimal("0.16")
                    ),
                    new AliquotaFaixa(
                            new BigDecimal("5000"),
                            null,
                            new BigDecimal("0.20")
                    )
            )
    );

    @Override
    public BigDecimal calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens) {

        return faixas.get(regime).stream()
                .filter(f -> valorTotalItens.compareTo(f.limiteMinimo()) >= 0
                        && (f.limiteMaximo() == null
                        || valorTotalItens.compareTo(f.limiteMaximo()) < 0))
                .map(AliquotaFaixa::aliquota)
                .findFirst()
                .orElseThrow();
    }
}
