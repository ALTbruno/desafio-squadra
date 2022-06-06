package com.altbruno.desafiosquadra.repository;

import com.altbruno.desafiosquadra.model.Bairro;
import com.altbruno.desafiosquadra.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Integer> {

	Optional<Bairro> findByCodigoBairro(Integer codigoBairro);
	Boolean existsByNomeAndMunicipio(String nome, Municipio municipio);
	List<Bairro> findAllByOrderByCodigoBairroDesc();
}
