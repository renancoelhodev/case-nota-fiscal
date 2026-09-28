package br.com.itau.geradornotafiscal.adapter.in;

import br.com.itau.geradornotafiscal.application.port.in.EmitirNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.domain.model.NotaFiscal;
import jakarta.validation.Valid;

import br.com.itau.geradornotafiscal.domain.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedido")
public class GeradorNFController {

	private EmitirNotaFiscalUseCase emitirNotaFiscal;

	public GeradorNFController(EmitirNotaFiscalUseCase emitirNotaFiscal) {
		this.emitirNotaFiscal = emitirNotaFiscal;
	}

	@PostMapping("/gerarNotaFiscal")
	public ResponseEntity<NotaFiscal> gerarNotaFiscal(@RequestBody @Valid Pedido pedido) {

		NotaFiscal notaFiscal = emitirNotaFiscal.emitirNotaFiscal(pedido);
		return new ResponseEntity<>(notaFiscal, HttpStatus.CREATED);
	}
	
}
