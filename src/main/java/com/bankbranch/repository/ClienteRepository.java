package com.bankbranch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankbranch.domain.Customer;

@Repository
public interface ClienteRepository extends JpaRepository<Customer, Integer> {
    Customer findByCpf(String cpf);
}
