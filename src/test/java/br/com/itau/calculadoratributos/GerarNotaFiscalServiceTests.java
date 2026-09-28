package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.application.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.application.service.CalculadoraFrete;
import br.com.itau.geradornotafiscal.application.usecases.GerarNotaFiscalService;
import br.com.itau.geradornotafiscal.domain.model.*;
import br.com.itau.geradornotafiscal.domain.model.enums.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.domain.model.enums.TipoPessoa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GerarNotaFiscalServiceTests {

    @Mock
    private CalculadoraAliquotaProduto calculadoraAliquotaProduto;

    @Mock
    private CalculadoraFrete calculadoraFrete;

    @InjectMocks
    private GerarNotaFiscalService service;

    @Test
    void deveGerarNotaFiscal() {

        Destinatario destinatario = Destinatario.builder()
                .tipoPessoa(TipoPessoa.FISICA)
                .regimeTributacao(RegimeTributacaoPJ.SIMPLES_NACIONAL)
                .enderecos(List.of())
                .build();

        Item item = Item.builder()
                .idItem("1")
                .descricao("Produto")
                .valorUnitario(new BigDecimal("100"))
                .quantidade(2)
                .build();

        Pedido pedido = Pedido.builder()
                .destinatario(destinatario)
                .valorTotalItens(new BigDecimal("200"))
                .valorFrete(new BigDecimal("20"))
                .itens(List.of(item))
                .build();

        when(calculadoraAliquotaProduto.calculaAliquota(any(), any(), any()))
                .thenReturn(new BigDecimal("0.10"));

        when(calculadoraFrete.calcularFrete(any(), any()))
                .thenReturn(new BigDecimal("25"));

        NotaFiscal resultado = service.gerarNotaFiscal(pedido);

        assertEquals(new BigDecimal("200"), resultado.getValorTotalItens());
        assertEquals(new BigDecimal("25"), resultado.getValorFrete());
    }

    @Test
    void deveCalcularTributoDoItem() {

        Item item = Item.builder()
                .idItem("1")
                .descricao("Produto")
                .valorUnitario(new BigDecimal("100"))
                .quantidade(2)
                .build();

        Destinatario destinatario = Destinatario.builder()
                .tipoPessoa(TipoPessoa.FISICA)
                .regimeTributacao(RegimeTributacaoPJ.SIMPLES_NACIONAL)
                .enderecos(List.of())
                .build();

        Pedido pedido = Pedido.builder()
                .destinatario(destinatario)
                .valorTotalItens(new BigDecimal("200"))
                .valorFrete(new BigDecimal("20"))
                .itens(List.of(item))
                .build();

        when(calculadoraAliquotaProduto.calculaAliquota(any(), any(), any()))
                .thenReturn(new BigDecimal("0.10"));

        when(calculadoraFrete.calcularFrete(any(), any()))
                .thenReturn(new BigDecimal("20"));

        NotaFiscal resultado = service.gerarNotaFiscal(pedido);

        assertEquals(
                new BigDecimal("10.00"),
                resultado.getItens().get(0).getValorTributoItem()
        );
    }

    @Test
    void deveLancarExcecaoAoCalcularAliquota() {

        Pedido pedido = Pedido.builder()
                .destinatario(Destinatario.builder()
                        .tipoPessoa(TipoPessoa.FISICA)
                        .regimeTributacao(RegimeTributacaoPJ.SIMPLES_NACIONAL)
                        .enderecos(List.of())
                        .build())
                .valorTotalItens(new BigDecimal("200"))
                .valorFrete(new BigDecimal("20"))
                .itens(List.of())
                .build();

        RuntimeException erro = new RuntimeException("Erro");

        when(calculadoraAliquotaProduto.calculaAliquota(any(), any(), any()))
                .thenThrow(erro);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.gerarNotaFiscal(pedido)
        );

        assertEquals("Erro", exception.getMessage());
    }
}