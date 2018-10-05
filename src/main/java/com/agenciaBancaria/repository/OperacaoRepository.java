package com.agenciaBancaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agenciaBancaria.domain.Operacao;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Integer> {

    @Query(value = "SELECT o.* FROM Operacao as o WHERE o.ID_CONTA_ORIGEM = ?1 or o.ID_CONTA_DESTINO = ?1", nativeQuery = true)
    List<Operacao> buscarExtrato(Integer id);



}
