package com.altbruno.desafiosquadra.repository;

import com.altbruno.desafiosquadra.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
	Optional<Endereco> findByCodigoEndereco(Integer codigoEndereco);
	void deleteByCodigoEndereco(Integer codigoEndereco);
}
