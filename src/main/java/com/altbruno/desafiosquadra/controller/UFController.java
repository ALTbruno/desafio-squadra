package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.model.UF;
import com.altbruno.desafiosquadra.repository.UFRepository;
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
	public ResponseEntity<List<UF>> salvarUF(@Valid @RequestBody UF uf) {
		return ResponseEntity.status(HttpStatus.OK).body(ufService.salvar(uf));
	}

	@GetMapping
	public ResponseEntity<List<UF>> listar(UF uf) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
		Example<UF> example = Example.of(uf, matcher);
		return ResponseEntity.ok(ufService.listar(example));
	}

	@PutMapping
	public ResponseEntity<List<UF>> editarPeloCodigoUF(@RequestBody UF uf) {
		return ResponseEntity.status(HttpStatus.OK).body(ufService.atualizar(uf));
	}

	@DeleteMapping("/{codigoUF}")
	public ResponseEntity<List<UF>> inativarPeloCodigoUF(@PathVariable Integer codigoUF) {
		return ResponseEntity.ok(ufService.inativar(codigoUF));
	}
}
