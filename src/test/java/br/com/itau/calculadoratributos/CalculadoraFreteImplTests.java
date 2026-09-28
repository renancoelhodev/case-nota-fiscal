package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.application.service.impl.CalculadoraFreteImpl;
import br.com.itau.geradornotafiscal.domain.model.Endereco;
import br.com.itau.geradornotafiscal.domain.model.enums.Finalidade;
import br.com.itau.geradornotafiscal.domain.model.enums.Regiao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraFreteImplTests {

    private final CalculadoraFreteImpl calculadora = new CalculadoraFreteImpl();

    @Test
    void deveCalcularFreteParaRegiaoNorte() {

        Endereco endereco = Endereco.builder()
                .finalidade(Finalidade.ENTREGA)
                .regiao(Regiao.NORTE)
                .build();

        BigDecimal resultado = calculadora.calcularFrete(
                new BigDecimal("100"),
                List.of(endereco)
        );

        assertEquals(new BigDecimal("108.00"), resultado);
    }

    @Test
    void deveCalcularFreteParaRegiaoSudeste() {

        Endereco endereco = Endereco.builder()
                .finalidade(Finalidade.COBRANCA_ENTREGA)
                .regiao(Regiao.SUDESTE)
                .build();

        BigDecimal resultado = calculadora.calcularFrete(
                new BigDecimal("100"),
                List.of(endereco)
        );

        assertEquals(new BigDecimal("104.800"), resultado);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarRegiao() {

        Endereco endereco = Endereco.builder()
                .finalidade(Finalidade.COBRANCA)
                .regiao(Regiao.SUDESTE)
                .build();

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> calculadora.calcularFrete(
                        new BigDecimal("100"),
                        List.of(endereco)
                )
        );

        assertEquals("Região não encontrada", exception.getMessage());
    }
}