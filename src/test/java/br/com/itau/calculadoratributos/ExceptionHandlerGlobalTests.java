package br.com.itau.calculadoratributos;

import br.com.itau.geradornotafiscal.domain.constant.Constantes;
import br.com.itau.geradornotafiscal.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionHandlerGlobalTests {

    private final ExceptionHandlerGlobal handler = new ExceptionHandlerGlobal();

    @Test
    void deveRetornarBadGatewayParaFalhaNaBaixaEstoque() {

        ResponseEntity<ExceptionHandlerGlobal.ErrorResponse> response =
                handler.handlerFalhaBaixaEstoqueException(
                        new FalhaBaixaEstoqueException(
                                "Erro",
                                new RuntimeException("Erro no estoque")
                        )
                );

        assertEquals(502, response.getStatusCode().value());
        assertEquals(
                Constantes.MENSAGEM_ERRO_BAD_GATEWAY,
                response.getBody().mensagem()
        );
    }

    @Test
    void deveRetornarBadGatewayParaFalhaNoRegistroNotaFiscal() {

        ResponseEntity<ExceptionHandlerGlobal.ErrorResponse> response =
                handler.handlerFalhaRegistroNotaFiscalException(
                        new FalhaRegistroNotaFiscalException(
                                "Erro",
                                new RuntimeException("Erro no registro")
                        )
                );

        assertEquals(502, response.getStatusCode().value());
        assertEquals(
                Constantes.MENSAGEM_ERRO_BAD_GATEWAY,
                response.getBody().mensagem()
        );
    }

    @Test
    void deveRetornarBadGatewayParaFalhaNoAgendamentoEntrega() {

        ResponseEntity<ExceptionHandlerGlobal.ErrorResponse> response =
                handler.handlerFalhaAgendamentoEntregaException(
                        new FalhaAgendamentoEntregaException(
                                "Erro",
                                new RuntimeException("Erro na entrega")
                        )
                );

        assertEquals(502, response.getStatusCode().value());
        assertEquals(
                Constantes.MENSAGEM_ERRO_BAD_GATEWAY,
                response.getBody().mensagem()
        );
    }

    @Test
    void deveRetornarBadGatewayParaFalhaNoFinanceiro() {

        ResponseEntity<ExceptionHandlerGlobal.ErrorResponse> response =
                handler.handlerFalhaEnvioFinanceiroException(
                        new FalhaEnvioFinanceiroException(
                                "Erro",
                                new RuntimeException("Erro no financeiro")
                        )
                );

        assertEquals(502, response.getStatusCode().value());
        assertEquals(
                Constantes.MENSAGEM_ERRO_BAD_GATEWAY,
                response.getBody().mensagem()
        );
    }

    @Test
    void deveRetornarBadGatewayParaFalhaAoGerarNotaFiscal() {

        ResponseEntity<ExceptionHandlerGlobal.ErrorResponse> response =
                handler.handler(
                        new FalhaGerarNotaFiscalException(
                                "Erro",
                                new RuntimeException("Erro ao gerar nota")
                        )
                );

        assertEquals(502, response.getStatusCode().value());
        assertEquals(
                Constantes.MENSAGEM_ERRO_BAD_GATEWAY,
                response.getBody().mensagem()
        );
    }
}