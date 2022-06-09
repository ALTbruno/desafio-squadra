package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.dto.post.PessoaDtoPost;
import com.altbruno.desafiosquadra.dto.put.PessoaDtoPut;
import com.altbruno.desafiosquadra.dto.post.PessoaDtoResumido;
import com.altbruno.desafiosquadra.model.Pessoa;
import com.altbruno.desafiosquadra.service.PessoaService;
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
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<List<PessoaDtoResumido>> cadastrar(@Valid @RequestBody PessoaDtoPost pessoaDtoPost) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.cadastrar(pessoaDtoPost));
	}

	@GetMapping
	public ResponseEntity<?> buscar(Pessoa pessoa) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
		Example<Pessoa> example = Example.of(pessoa, matcher);

		if (pessoa.getCodigoPessoa() != null && pessoaService.codigoPessoaValido(pessoa.getCodigoPessoa()))
			return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscaPorCodigoPessoa(pessoa.getCodigoPessoa()));
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarPessoasResumido(example));
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
