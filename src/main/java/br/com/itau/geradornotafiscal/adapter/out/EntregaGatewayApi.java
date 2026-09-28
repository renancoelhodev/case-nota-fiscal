package br.com.itau.geradornotafiscal.adapter.out;

import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import br.com.itau.geradornotafiscal.application.port.out.EntregaGateway;
import org.springframework.stereotype.Service;

@Service
public class EntregaGatewayApi implements EntregaGateway {
    public void agendarEntrega(NotaFiscal notaFiscal) {

            try {
                //Simula o agendamento da entrega
                Thread.sleep(150);
                new EntregaIntegrationAdapter().criarAgendamentoEntrega(notaFiscal);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

    }
}
