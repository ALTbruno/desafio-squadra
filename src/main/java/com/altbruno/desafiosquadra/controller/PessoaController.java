package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.dto.PessoaDtoBuscaPorCodigoPessoa;
import com.altbruno.desafiosquadra.dto.PessoaDtoPost;
import com.altbruno.desafiosquadra.dto.PessoaDtoPut;
import com.altbruno.desafiosquadra.dto.PessoaDtoResumido;
import com.altbruno.desafiosquadra.model.Pessoa;
import com.altbruno.desafiosquadra.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<List<PessoaDtoResumido>> cadastrar(@Valid @RequestBody PessoaDtoPost pessoaDtoPost) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.cadastrar(pessoaDtoPost));
	}

	@GetMapping
	public ResponseEntity<?> buscar(@RequestParam(required = false) Integer codigoPessoa) {
		if (codigoPessoa == null) return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarPessoasResumido());
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscaPorCodigoPessoa(codigoPessoa));
	}

	@PutMapping
	public ResponseEntity<List<PessoaDtoResumido>> atualizar(@Valid @RequestBody PessoaDtoPut pessoaDto) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.atualizar(pessoaDto));
	}

	@DeleteMapping("/{codigoPessoa}")
	public ResponseEntity<List<PessoaDtoResumido>> inativarPeloCodigoPessoa(@PathVariable Integer codigoPessoa) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.inativar(codigoPessoa));
	}
}
