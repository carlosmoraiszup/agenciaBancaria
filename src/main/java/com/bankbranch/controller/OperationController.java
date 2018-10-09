package com.bankbranch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;
import com.bankbranch.dto.OperationDepositoDTO;
import com.bankbranch.dto.OperationExtractDTO;
import com.bankbranch.dto.OperationWithdrawDTO;
import com.bankbranch.service.OperationService;

@RestController
@RequestMapping(value = "/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping(value = "/searchBalance/{id}")
    public ResponseEntity<Account> searchBalance(@PathVariable Integer id) {
        Account account = operationService.findAccount(id);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping(value = "/deposits")
    public ResponseEntity<OperationDepositoDTO> depositMoney(@RequestBody Operation operation) {
        operation.setOperationType(OperationType.DEPOSIT);
        Operation newOperation = operationService.typeOperation(operation);
        OperationDepositoDTO operationDTO = new OperationDepositoDTO(newOperation);
        return ResponseEntity.ok().body(operationDTO);
    }

    @PostMapping(value = "/withdraw")
    public ResponseEntity<OperationWithdrawDTO> withdrawMoney(@RequestBody Operation operation) {
        operation.setOperationType(OperationType.WITHDRAW);
        Operation newOperation = operationService.typeOperation(operation);
        OperationWithdrawDTO operationDTO = new OperationWithdrawDTO(newOperation);
        return ResponseEntity.ok().body(operationDTO);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<Operation> transferMoney(@RequestBody Operation operation) {
        operation.setOperationType(OperationType.TRANSFER);
        Operation newOperation = operationService.typeOperation(operation);
        return ResponseEntity.ok().body(newOperation);
    }


    @GetMapping(value = "extract/{id}")
    public ResponseEntity<List<OperationExtractDTO>> extractAccount(@PathVariable Integer id){
        List<Operation> listOperation = operationService.findExtract(id);
        List<OperationExtractDTO> listOperationDTO =
                listOperation.stream().map(newOperation -> new OperationExtractDTO(newOperation)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listOperationDTO);
    }

}