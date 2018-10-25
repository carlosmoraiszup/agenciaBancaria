package com.bankbranch.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "findAccountBalance")
    public Account searchBalance() {
        return operationService.findAccount();
    }

    @PostMapping(value = "/depositIntoAccount/{destinationAccount}")
    public OperationDepositoDTO depositMoney(@RequestBody Operation operation,
            @PathVariable Integer destinationAccount) {
        operation.setOperationType(OperationType.DEPOSIT);
        Operation newOperation = operationService.typeOperation(operation, destinationAccount);
        OperationDepositoDTO operationDTO = new OperationDepositoDTO(newOperation);
        return operationDTO;
    }

    @PostMapping(value = "/withdrawIntoAccount")
    public OperationWithdrawDTO withdrawMoney(@RequestBody Operation operation) {
        operation.setOperationType(OperationType.WITHDRAW);
        Operation newOperation = operationService.typeOperation(operation, null);
        OperationWithdrawDTO operationDTO = new OperationWithdrawDTO(newOperation);
        return operationDTO;
    }

    @PostMapping(value = "/transferMoneyTo/{destinationAccount}")
    public OperationTransferDTO transferMoney(@RequestBody Operation operation,
            @PathVariable Integer destinationAccount) {
        operation.setOperationType(OperationType.TRANSFER);
        Operation newOperation = operationService.typeOperation(operation, destinationAccount);
        OperationTransferDTO operationDTO = new OperationTransferDTO(newOperation);
        return operationDTO;
    }


    @GetMapping(value = "/extractAccount")
    public List<OperationExtractDTO> extractAccount() {
        List<Operation> listOperation = operationService.findExtract();

        List<OperationExtractDTO> listOperationDTO =
                listOperation.stream().map(newOperation -> new OperationExtractDTO(newOperation)).collect(
                        Collectors.toList());

        OperationExtractDTO balance = new OperationExtractDTO(operationService.findAccount().getBalance(),
                operationService.findAccount().getNumberAccount());
        listOperationDTO.add(balance);

        return listOperationDTO;
    }

}