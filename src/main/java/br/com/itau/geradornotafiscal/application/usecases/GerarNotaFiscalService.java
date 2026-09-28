package br.com.itau.geradornotafiscal.application.usecases;

import br.com.itau.geradornotafiscal.application.port.in.GerarNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.application.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.application.service.CalculadoraFrete;
import br.com.itau.geradornotafiscal.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GerarNotaFiscalService implements GerarNotaFiscalUseCase {

	private static Logger logger = LoggerFactory.getLogger(GerarNotaFiscalService.class);

	private final CalculadoraAliquotaProduto calculadoraAliquotaProduto;
	private final CalculadoraFrete calculadoraFrete;


    public GerarNotaFiscalService(CalculadoraAliquotaProduto calculadoraAliquotaProduto,
								  CalculadoraFrete calculadoraFrete) {
        this.calculadoraAliquotaProduto = calculadoraAliquotaProduto;
        this.calculadoraFrete = calculadoraFrete;
    }

	@Override
	public NotaFiscal gerarNotaFiscal(Pedido pedido) {

		logger.debug("Informações do destinatário: ");
		logger.debug(pedido.getDestinatario().getTipoPessoa().toString());
		logger.debug(pedido.getDestinatario().getRegimeTributacao().toString());
		Destinatario destinatario = pedido.getDestinatario();


		BigDecimal aliquota = calculadoraAliquotaProduto.calculaAliquota(destinatario.getTipoPessoa(),
				destinatario.getRegimeTributacao(),
				pedido.getValorTotalItens());

		logger.debug("Aliquota: " + aliquota);

		List<ItemNotaFiscal> itens = criarListaNotaFiscal(pedido.getItens(), aliquota);

		BigDecimal valorFreteComPercentual = calculadoraFrete.calcularFrete(pedido.getValorFrete(), destinatario.getEnderecos());

		return NotaFiscal.builder()
				.idNotaFiscal(UUID.randomUUID().toString())
				.data(LocalDateTime.now())
				.valorTotalItens(pedido.getValorTotalItens())
				.valorFrete(valorFreteComPercentual)
				.itens(itens)
				.destinatario(pedido.getDestinatario())
				.build();
	}

	private List<ItemNotaFiscal> criarListaNotaFiscal(List<Item> itens, BigDecimal aliquotaPercentual) {

		List<ItemNotaFiscal> itensNotaFiscal = new ArrayList<>();

		for (Item item : itens) {
			BigDecimal valorTributo = item.getValorUnitario().multiply(aliquotaPercentual);
			ItemNotaFiscal itemNotaFiscal = ItemNotaFiscal.builder()
					.idItem(item.getIdItem())
					.descricao(item.getDescricao())
					.valorUnitario(item.getValorUnitario())
					.quantidade(item.getQuantidade())
					.valorTributoItem(valorTributo)
					.build();
			itensNotaFiscal.add(itemNotaFiscal);
		}
		return itensNotaFiscal;
	}

}