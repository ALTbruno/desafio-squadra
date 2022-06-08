package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.model.UF;
import com.altbruno.desafiosquadra.service.UFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/uf")
public class UFController {

	@Autowired
	private UFService ufService;

	@PostMapping
	public ResponseEntity<List<UF>> salvarUF(@Valid @RequestBody com.altbruno.desafiosquadra.dto.post.UF ufDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(ufService.salvar(ufDTO));
	}

	@GetMapping
	public ResponseEntity<?> listar(UF uf) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
		Example<UF> example = Example.of(uf, matcher);

		List<UF> resultadoBusca = ufService.listar(example);

		if ((uf.getCodigoUF() != null || uf.getNome() != null || uf.getSigla() != null) && resultadoBusca.size() > 0)
			return ResponseEntity.status(HttpStatus.OK).body(resultadoBusca.get(0));
		return ResponseEntity.status(HttpStatus.OK).body(resultadoBusca);
	}

	@PutMapping
	public ResponseEntity<List<UF>> editarPeloCodigoUF(@Valid @RequestBody UF uf) {
		return ResponseEntity.status(HttpStatus.OK).body(ufService.atualizar(uf));
	}

	@DeleteMapping("/{codigoUF}")
	public ResponseEntity<List<UF>> inativarPeloCodigoUF(@PathVariable Integer codigoUF) {
		return ResponseEntity.status(HttpStatus.OK).body(ufService.inativar(codigoUF));
	}
}
