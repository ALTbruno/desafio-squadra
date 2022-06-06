package com.altbruno.desafiosquadra.repository;

import com.altbruno.desafiosquadra.model.Municipio;
import com.altbruno.desafiosquadra.model.UF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

	List<Municipio> findAllByOrderByCodigoMunicipioDesc();
	Boolean existsByNomeAndUf(String nome, UF uf);
	Optional<Municipio> findByCodigoMunicipio(Integer codigoUf);
}
