package com.agenciaBancaria.repository;

import com.agenciaBancaria.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Integer> {

    @Query("SELECT o FROM Operacao o WHERE idDestino = ?1 or o.id_Origem =  ?1")
    public List<Operacao> operacao(Integer id);



}
