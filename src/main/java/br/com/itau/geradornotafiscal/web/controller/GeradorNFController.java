package br.com.itau.geradornotafiscal.web.controller;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.service.GerarNotaFiscalUseCase;
import br.com.itau.geradornotafiscal.usecases.EmitirNotaFiscalUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.geradornotafiscal.model.Pedido;
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

		// Exemplo de retorno
		NotaFiscal notaFiscal = emitirNotaFiscal.emitirNotaFiscal(pedido);
		return new ResponseEntity<>(notaFiscal, HttpStatus.CREATED);
	}
	
}
