package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.adapter.in.GeradorNFController;
import br.com.itau.geradornotafiscal.application.port.in.EmitirNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import br.com.itau.geradornotafiscal.domain.model.Pedido;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class GeradorNFControllerTests {

    @Mock
    private EmitirNotaFiscalUseCase emitirNotaFiscal;

    @Mock
    private Pedido pedido;

    @Mock
    private NotaFiscal notaFiscal;

    @InjectMocks
    private GeradorNFController controller;

    @Test
    void deveGerarNotaFiscalComSucesso() {

        when(emitirNotaFiscal.emitirNotaFiscal(pedido))
                .thenReturn(notaFiscal);

        ResponseEntity<NotaFiscal> response =
                controller.gerarNotaFiscal(pedido);

        assertEquals(201, response.getStatusCode().value());
        assertSame(notaFiscal, response.getBody());
    }
}