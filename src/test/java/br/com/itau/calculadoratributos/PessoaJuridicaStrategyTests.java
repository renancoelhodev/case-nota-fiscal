package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.domain.strategy.PessoaJuridicaStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaJuridicaStrategyTests {

    private final PessoaJuridicaStrategy strategy = new PessoaJuridicaStrategy();

    @Test
    void deveCalcularAliquotaDoSimplesNacional() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.SIMPLES_NACIONAL,
                new BigDecimal("3000")
        );

        assertEquals(new BigDecimal("0.13"), resultado);
    }

    @Test
    void deveCalcularAliquotaDoLucroReal() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.LUCRO_REAL,
                new BigDecimal("3000")
        );

        assertEquals(new BigDecimal("0.15"), resultado);
    }

    @Test
    void deveCalcularAliquotaDoLucroPresumido() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.LUCRO_PRESUMIDO,
                new BigDecimal("3000")
        );

        assertEquals(new BigDecimal("0.16"), resultado);
    }
}