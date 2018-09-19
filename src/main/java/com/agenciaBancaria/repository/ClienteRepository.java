package com.agenciaBancaria.repository;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
