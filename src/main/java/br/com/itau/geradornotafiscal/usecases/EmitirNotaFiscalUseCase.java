package br.com.itau.geradornotafiscal.usecases;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.service.*;
import br.com.itau.geradornotafiscal.service.impl.GeradorNotaFiscalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static br.com.itau.geradornotafiscal.Constantes.Mensagem;

@Service
public class EmitirNotaFiscalUseCase {

    private static Logger logger = LoggerFactory.getLogger(EmitirNotaFiscalUseCase.class);

    private final EstoqueService estoqueService;
    private final RegistroService registroService;
    private final EntregaService entregaService;
    private final FinanceiroService financeiroService;
    private final GerarNotaFiscalUseCase gerarNotaFiscalUseCase;


    public EmitirNotaFiscalUseCase(EstoqueService estoqueService, RegistroService registroService, EntregaService entregaService, FinanceiroService financeiroService, GerarNotaFiscalUseCase gerarNotaFiscalUseCase) {
        this.estoqueService = estoqueService;
        this.registroService = registroService;
        this.entregaService = entregaService;
        this.financeiroService = financeiroService;
        this.gerarNotaFiscalUseCase = gerarNotaFiscalUseCase;
    }


    public NotaFiscal emitirNotaFiscal(Pedido pedido) {

        NotaFiscal notaFiscal = gerarNotaFiscalUseCase.gerarNotaFiscal(pedido);

        try {
            estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal);
        } catch (Exception ex) //exception especifica pra falha na baixa do estoque
        {
            throw ex;
        }
        registroService.registrarNotaFiscal(notaFiscal);
        entregaService.agendarEntrega(notaFiscal);
        financeiroService.enviarNotaFiscalParaContasReceber(notaFiscal);

        logger.debug(String.format(Mensagem, pedido.getIdPedido()));

        return notaFiscal;
    }
}
