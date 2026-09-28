package br.com.itau.geradornotafiscal.application.service.impl;

import br.com.itau.geradornotafiscal.domain.model.Endereco;
import br.com.itau.geradornotafiscal.domain.model.enums.Finalidade;
import br.com.itau.geradornotafiscal.domain.model.enums.Regiao;
import br.com.itau.geradornotafiscal.application.service.CalculadoraFrete;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CalculadoraFreteImpl implements CalculadoraFrete {

    private final Map<Regiao, BigDecimal> multiplicadores = Map.of(
            Regiao.NORTE, new BigDecimal("1.08"),
            Regiao.NORDESTE, new BigDecimal("1.085"),
            Regiao.CENTRO_OESTE, new BigDecimal("1.07"),
            Regiao.SUDESTE, new BigDecimal("1.048"),
            Regiao.SUL, new BigDecimal("1.06")
    );

    private Regiao obterRegiao(List<Endereco> enderecos) {
        return enderecos.stream()
                .filter(endereco ->
                        endereco.getFinalidade() == Finalidade.ENTREGA ||
                                endereco.getFinalidade() == Finalidade.COBRANCA_ENTREGA)
                .map(Endereco::getRegiao)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Região não encontrada"));
    }

    public BigDecimal calcularFrete(BigDecimal valorFrete, List<Endereco> enderecos) {

        Regiao regiao = obterRegiao(enderecos);

        BigDecimal multiplicador = multiplicadores.get(regiao);

        return valorFrete.multiply(multiplicador);
    }
}
