package br.com.itau.geradornotafiscal.domain.strategy;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PessoaFisicaStrategy implements TributacaoStrategy {

    public record AliquotaFaixaPF(
            BigDecimal limiteMinimo,
            BigDecimal limiteMaximo,
            BigDecimal aliquota
    ) {}

    private final List<AliquotaFaixaPF> faixas = List.of(
            new AliquotaFaixaPF(
                    BigDecimal.ZERO,
                    new BigDecimal("500"),
                    new BigDecimal("0")
            ),
            new AliquotaFaixaPF(
                    new BigDecimal("500"),
                    new BigDecimal("2000"),
                    new BigDecimal("0.12")
            ),
            new AliquotaFaixaPF(
                    new BigDecimal("2000"),
                    new BigDecimal("3500"),
                    new BigDecimal("0.15")
            ),
            new AliquotaFaixaPF(
                    new BigDecimal("3500"),
                    null,
                    new BigDecimal("0.17")
            )
    );

    @Override
    public BigDecimal calcular(RegimeTributacaoPJ regime, BigDecimal valorTotalItens) {

        return faixas.stream()
                .filter(f -> valorTotalItens.compareTo(f.limiteMinimo()) >= 0
                        && (f.limiteMaximo() == null
                        || valorTotalItens.compareTo(f.limiteMaximo()) < 0))
                .map(AliquotaFaixaPF::aliquota)
                .findFirst()
                .orElseThrow();
    }
}
