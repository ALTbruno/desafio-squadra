package com.altbruno.desafiosquadra.repository;

import com.altbruno.desafiosquadra.model.UF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.text.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface UFRepository extends JpaRepository<UF, Integer> {
	List<UF> findAllByOrderByCodigoUFDesc();
	Optional<UF> findByCodigoUF(Integer codigoUf);
	Boolean existsByNome(String nomeUF);
	Boolean existsBySigla(String siglaUF);
}
