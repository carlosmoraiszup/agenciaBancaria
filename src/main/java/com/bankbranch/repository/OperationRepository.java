package com.bankbranch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bankbranch.domain.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query(value = "SELECT o.* FROM Operation as o WHERE o.ORIGIN_ACCOUNT = ?1 or o.DESTINATION_ACCOUNT" +
            " = ?1",
            nativeQuery = true)
    List<Operation> searchExtract(Integer id);
}
