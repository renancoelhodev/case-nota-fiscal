package br.com.itau.geradornotafiscal.adapter.out;

import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import br.com.itau.geradornotafiscal.application.port.out.FinanceiroGateway;
import org.springframework.stereotype.Service;

@Service
public class FinanceiroGatewayApi implements FinanceiroGateway {
    public void enviarNotaFiscalParaContasReceber(NotaFiscal notaFiscal) {

        try {
            //Simula o envio da nota fiscal para o contas a receber
            Thread.sleep(250);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
