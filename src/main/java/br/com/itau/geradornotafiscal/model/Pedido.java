package br.com.itau.geradornotafiscal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Pedido {
	@JsonProperty("id_pedido")
	@NotNull
	@Positive
	private Integer idPedido;

	@JsonProperty("data")
	@NotNull
	private LocalDate data;

	@JsonProperty("valor_total_itens")
	@NotNull
	@PositiveOrZero
	private BigDecimal valorTotalItens;

	@JsonProperty("valor_frete")
	@NotNull
	@PositiveOrZero
	private BigDecimal valorFrete;

	@JsonProperty("itens")
	@NotEmpty
	@Valid
	private List<Item> itens;

	@JsonProperty("destinatario")
	@NotNull
	@Valid
	private Destinatario destinatario;

}
