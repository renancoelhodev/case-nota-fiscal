package br.com.itau.geradornotafiscal.application.usecases;

import br.com.itau.geradornotafiscal.application.port.in.EmitirNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.application.port.in.GerarNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.application.port.out.*;
import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import br.com.itau.geradornotafiscal.domain.model.Pedido;
import br.com.itau.geradornotafiscal.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static br.com.itau.geradornotafiscal.domain.constant.Constantes.MENSAGEM_SUCESSO;

@Service
public class EmitirNotaFiscalService implements EmitirNotaFiscalUseCase {

    private static Logger logger = LoggerFactory.getLogger(EmitirNotaFiscalService.class);

    private final EstoqueGateway estoqueGateway;
    private final RegistroGateway registroGateway;
    private final EntregaGateway entregaGateway;
    private final FinanceiroGateway financeiroGateway;
    private final GerarNotaFiscalUseCase gerarNotaFiscalUseCase;


    public EmitirNotaFiscalService(EstoqueGateway estoqueGateway, RegistroGateway registroGateway, EntregaGateway entregaGateway, FinanceiroGateway financeiroGateway, GerarNotaFiscalUseCase gerarNotaFiscalUseCase) {
        this.estoqueGateway = estoqueGateway;
        this.registroGateway = registroGateway;
        this.entregaGateway = entregaGateway;
        this.financeiroGateway = financeiroGateway;
        this.gerarNotaFiscalUseCase = gerarNotaFiscalUseCase;
    }


    public NotaFiscal emitirNotaFiscal(Pedido pedido) {

        NotaFiscal notaFiscal;

        try {
            notaFiscal = gerarNotaFiscalUseCase.gerarNotaFiscal(pedido);
        } catch (Exception ex) {
            logger.error("Erro ao realizar geração de nota fiscal: {}", ex.getMessage(), ex);
            throw new FalhaGerarNotaFiscalException(
                    "Falha ao gerar nota fiscal", ex);
        }

        try {
            estoqueGateway.enviarNotaFiscalParaBaixaEstoque(notaFiscal);
        } catch (Exception ex) {
            logger.error("Erro ao realizar baixa do estoque: {}", ex.getMessage(), ex);

            throw new FalhaBaixaEstoqueException(
                    "Falha ao realizar baixa do estoque", ex);
        }

        try {
            registroGateway.registrarNotaFiscal(notaFiscal);
        } catch (Exception ex) {
            logger.error("Erro ao registrar nota fiscal: {}", ex.getMessage(), ex);

            throw new FalhaRegistroNotaFiscalException(
                    "Falha ao registrar nota fiscal", ex);
        }

        try {
            entregaGateway.agendarEntrega(notaFiscal);
        } catch (Exception ex) {
            logger.error("Erro ao agendar entrega: {}", ex.getMessage(), ex);

            throw new FalhaAgendamentoEntregaException(
                    "Falha ao agendar entrega", ex);
        }

        try {
            financeiroGateway.enviarNotaFiscalParaContasReceber(notaFiscal);
        } catch (Exception ex) {
            logger.error("Erro ao enviar nota fiscal para o financeiro: {}",
                    ex.getMessage(), ex);

            throw new FalhaEnvioFinanceiroException(
                    "Falha ao enviar nota fiscal para o financeiro", ex);
        }

        logger.debug(String.format(MENSAGEM_SUCESSO, pedido.getIdPedido()));

        return notaFiscal;
    }
}
