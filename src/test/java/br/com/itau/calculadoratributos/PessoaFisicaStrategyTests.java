package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.domain.strategy.PessoaFisicaStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaFisicaStrategyTests {

    private final PessoaFisicaStrategy strategy = new PessoaFisicaStrategy();

    @Test
    void deveRetornarAliquotaZeroParaValorAbaixoDe500() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.SIMPLES_NACIONAL,
                new BigDecimal("400")
        );

        assertEquals(new BigDecimal("0"), resultado);
    }

    @Test
    void deveRetornarAliquota12PorCentoParaValorEntre500E2000() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.SIMPLES_NACIONAL,
                new BigDecimal("1000")
        );

        assertEquals(new BigDecimal("0.12"), resultado);
    }

    @Test
    void deveRetornarAliquota15PorCentoParaValorEntre2000E3500() {


        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.SIMPLES_NACIONAL,
                new BigDecimal("2500")
        );

        assertEquals(new BigDecimal("0.15"), resultado);
    }

    @Test
    void deveRetornarAliquota17PorCentoParaValorAcimaDe3500() {

        BigDecimal resultado = strategy.calcular(
                RegimeTributacaoPJ.SIMPLES_NACIONAL,
                new BigDecimal("4000")
        );

        assertEquals(new BigDecimal("0.17"), resultado);
    }
}