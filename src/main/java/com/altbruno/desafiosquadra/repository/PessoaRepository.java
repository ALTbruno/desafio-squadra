package com.altbruno.desafiosquadra.repository;

import com.altbruno.desafiosquadra.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	List<Pessoa> findAllByOrderByCodigoPessoaDesc();
	Optional<Pessoa> findByCodigoPessoa(Integer codigoPessoa);
	Boolean existsByLogin(String login);
	Boolean existsByCodigoPessoa(Integer codigoPessoa);
}
