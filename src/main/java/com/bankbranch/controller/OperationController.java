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
import com.bankbranch.dto.OperationTransferDTO;
import com.bankbranch.dto.OperationWithdrawDTO;
import com.bankbranch.service.OperationService;

@RestController
@RequestMapping(value = "/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @GetMapping(value = "/searchBalance/{idAccount}")
    public ResponseEntity<Account> searchBalance(@PathVariable Integer idAccount) {
        Account account = operationService.findAccount(idAccount);
        return ResponseEntity.ok().body(account);
    }

    @PostMapping(value = "/depositAccount/{destinationAccount}")
    public ResponseEntity<OperationDepositoDTO> depositMoney(@RequestBody Operation operation,
            @PathVariable Integer destinationAccount) {
        operation.setOperationType(OperationType.DEPOSIT);
        Operation newOperation = operationService.typeOperation(null, operation, destinationAccount);
        OperationDepositoDTO operationDTO = new OperationDepositoDTO(newOperation);
        return ResponseEntity.ok().body(operationDTO);
    }

    @PostMapping(value = "/withdrawAccount/{originAccount}")
    public ResponseEntity<OperationWithdrawDTO> withdrawMoney(@RequestBody Operation operation,
            @PathVariable Integer originAccount) {
        operation.setOperationType(OperationType.WITHDRAW);
        Operation newOperation = operationService.typeOperation(originAccount, operation, null);
        OperationWithdrawDTO operationDTO = new OperationWithdrawDTO(newOperation);
        return ResponseEntity.ok().body(operationDTO);
    }

    @PostMapping(value = "/{originAccount}/transferMoneyTo/{destinationAccount}")
    public ResponseEntity<OperationTransferDTO> transferMoney(@PathVariable Integer originAccount,
            @RequestBody Operation operation, @PathVariable Integer destinationAccount) {
        operation.setOperationType(OperationType.TRANSFER);
        Operation newOperation = operationService.typeOperation(originAccount, operation, destinationAccount);
        OperationTransferDTO operationDTO = new OperationTransferDTO(newOperation);
        return ResponseEntity.ok().body(operationDTO);
    }


    @GetMapping(value = "extractAccount/{numberAccount}")
    public ResponseEntity<List<OperationExtractDTO>> extractAccount(@PathVariable Integer numberAccount) {
        List<Operation> listOperation = operationService.findExtract(numberAccount);
        OperationExtractDTO balance = new OperationExtractDTO(numberAccount, operationService.findAccount(numberAccount).getBalance());
        List<OperationExtractDTO> listOperationDTO =
                listOperation.stream().map(newOperation -> new OperationExtractDTO(newOperation)).collect(
                        Collectors.toList());
        listOperationDTO.add(balance);
        return ResponseEntity.ok().body(listOperationDTO);
    }

}