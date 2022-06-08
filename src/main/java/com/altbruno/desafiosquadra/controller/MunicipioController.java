package com.altbruno.desafiosquadra.controller;

import com.altbruno.desafiosquadra.dto.get.MunicipioDtoGet;
import com.altbruno.desafiosquadra.dto.post.MunicipioDtoPost;
import com.altbruno.desafiosquadra.service.MunicipioService;
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
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioService municipioService;

	@PostMapping
	public ResponseEntity<List<MunicipioDtoGet>> cadastrar(@Valid @RequestBody MunicipioDtoPost municipioDtoPost) {
		return ResponseEntity.status(HttpStatus.OK).body(municipioService.cadastrar(municipioDtoPost));
	}

	@GetMapping
	ResponseEntity<?> listar(MunicipioDtoGet municipioDtoGet) {
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
		Example<MunicipioDtoGet> example = Example.of(municipioDtoGet, matcher);

		List<MunicipioDtoGet> resultadoBusca = municipioService.listar(example);

		if (municipioDtoGet.getCodigoMunicipio() != null  && resultadoBusca.size() > 0)
			return ResponseEntity.status(HttpStatus.OK).body(resultadoBusca.get(0));

		return ResponseEntity.status(HttpStatus.OK).body(resultadoBusca);
	}

	@PutMapping
	public ResponseEntity<List<MunicipioDtoGet>> editarPorCodigoMunicipio(@Valid @RequestBody MunicipioDtoGet municipioDtoGet) {
		return ResponseEntity.status(HttpStatus.OK).body(municipioService.atualizar(municipioDtoGet));
	}

	@DeleteMapping("/{codigoMunicipio}")
	public ResponseEntity<List<MunicipioDtoGet>> inativarPeloCodigoMunicipio(@PathVariable Integer codigoMunicipio) {
		return ResponseEntity.status(HttpStatus.OK).body(municipioService.inativar(codigoMunicipio));
	}
}
