package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.application.port.in.GerarNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.application.port.out.*;
import br.com.itau.geradornotafiscal.application.usecases.EmitirNotaFiscalService;
import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import br.com.itau.geradornotafiscal.domain.model.Pedido;
import br.com.itau.geradornotafiscal.exceptions.FalhaBaixaEstoqueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmitirNotaFiscalServiceTests {

    @Mock
    private EstoqueGateway estoqueGateway;

    @Mock
    private RegistroGateway registroGateway;

    @Mock
    private EntregaGateway entregaGateway;

    @Mock
    private FinanceiroGateway financeiroGateway;

    @Mock
    private GerarNotaFiscalUseCase gerarNotaFiscalUseCase;

    @Mock
    private Pedido pedido;

    @Mock
    private NotaFiscal notaFiscal;

    @InjectMocks
    private EmitirNotaFiscalService service;

    @Test
    void deveEmitirNotaFiscalComSucesso() {
        when(gerarNotaFiscalUseCase.gerarNotaFiscal(pedido))
                .thenReturn(notaFiscal);

        NotaFiscal resultado = service.emitirNotaFiscal(pedido);

        assertSame(notaFiscal, resultado);
    }

    @Test
    void deveLancarExcecaoQuandoFalharBaixaEstoque() {
        when(gerarNotaFiscalUseCase.gerarNotaFiscal(pedido))
                .thenReturn(notaFiscal);

        RuntimeException erro = new RuntimeException("Erro no estoque");

        doThrow(erro)
                .when(estoqueGateway)
                .enviarNotaFiscalParaBaixaEstoque(notaFiscal);

        FalhaBaixaEstoqueException exception = assertThrows(
                FalhaBaixaEstoqueException.class,
                () -> service.emitirNotaFiscal(pedido)
        );

        assertEquals("Falha ao realizar baixa do estoque", exception.getMessage());
        assertSame(erro, exception.getCause());
    }
}