package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.enums.Finalidade;
import br.com.itau.geradornotafiscal.model.enums.Regiao;
import br.com.itau.geradornotafiscal.service.CalculadoraFrete;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CalculadoraFreteImpl implements CalculadoraFrete {

    private Regiao obterRegiao(List<Endereco> enderecos) {
        return enderecos.stream()
                .filter(endereco -> endereco.getFinalidade() == Finalidade.ENTREGA || endereco.getFinalidade() == Finalidade.COBRANCA_ENTREGA)
                .map(Endereco::getRegiao)
                .findFirst()
                .orElse(null);
    }

    public BigDecimal calcularFrete(BigDecimal valorFrete, List<Endereco> enderecos) {

        Regiao regiao = obterRegiao(enderecos);
        if (regiao == Regiao.NORTE) {
            return valorFrete.multiply(new BigDecimal("1.08"));
        } else if (regiao == Regiao.NORDESTE) {
            return valorFrete.multiply(new BigDecimal("1.085"));
        } else if (regiao == Regiao.CENTRO_OESTE) {
            return valorFrete.multiply(new BigDecimal("1.07"));
        } else if (regiao == Regiao.SUDESTE) {
            return valorFrete.multiply(new BigDecimal("1.048"));
        } else if (regiao == Regiao.SUL) {
            return valorFrete.multiply(new BigDecimal("1.06"));
        }
        return valorFrete;
    }
}
