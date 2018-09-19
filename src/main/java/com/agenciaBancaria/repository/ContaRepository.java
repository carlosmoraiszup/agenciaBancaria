package com.agenciaBancaria.repository;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
}
