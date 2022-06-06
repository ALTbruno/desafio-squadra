package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.dto.BairroDtoGet;
import com.altbruno.desafiosquadra.dto.BairroDtoPost;
import com.altbruno.desafiosquadra.service.BairroService;
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
@RequestMapping("/bairro")
public class BairroController {

	@Autowired
	private BairroService bairroService;

	@PostMapping
	public ResponseEntity<List<BairroDtoGet>> cadastrar(@Valid @RequestBody BairroDtoPost bairroDtoPost) {
		return ResponseEntity.status(HttpStatus.OK).body(bairroService.cadastrar(bairroDtoPost));
	}

	@GetMapping
	ResponseEntity<List<BairroDtoGet>> listar(BairroDtoGet bairroDtoGet) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
		Example<BairroDtoGet> example = Example.of(bairroDtoGet, matcher);
		return ResponseEntity.status(HttpStatus.OK).body(bairroService.listar(example));
	}

	@PutMapping
	public ResponseEntity<List<BairroDtoGet>> editarPorCodigoBairro(@Valid @RequestBody BairroDtoGet bairroDtoGet) {
		return ResponseEntity.status(HttpStatus.OK).body(bairroService.atualizar(bairroDtoGet));
	}

	@DeleteMapping("/{codigoBairro}")
	public ResponseEntity<List<BairroDtoGet>> inativarPeloCodigoBairro(@PathVariable Integer codigoBairro) {
		return ResponseEntity.status(HttpStatus.OK).body(bairroService.inativar(codigoBairro));
	}
}
