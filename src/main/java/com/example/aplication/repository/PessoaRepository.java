package com.example.aplication.repository;

import com.example.aplication.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQueries {

    Optional<Pessoa> findByCpf(String cpf);

    @Query("SELECT p FROM Pessoa p JOIN p.telefones t WHERE t.ddd = :ddd AND t.numero = :numero")
    Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);

}
