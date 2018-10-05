package com.agenciaBancaria.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.dto.ClienteDTO;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.service.ClienteService;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;


    public Cliente findIdCustomer(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Override
    public Cliente registerCustomer(Cliente cliente, Conta conta) {

        if (null == conta)
            return null;

        cliente.setId(null);
        cliente.setDataCriacao(LocalDate.now().toString());
        cliente.setConta(conta);

        return clienteRepository.save(cliente);

    }

    @Override
    public List<Cliente> findAllCustomer() {
        return clienteRepository.findAll();
    }


    @Override
    public void deleteCustomer(Integer id) {
        findIdCustomer(id);
        clienteRepository.deleteById(id);
        contaRepository.deleteById(id);
    }

    @Override
    public Cliente updateCustomer(Cliente cliente) {
        Cliente newCliente = findIdCustomer(cliente.getId());
        if(null != cliente.getCpf())
            newCliente.setCpf(cliente.getCpf());
        if(null != cliente.getNome())
            newCliente.setNome(cliente.getNome());
        return clienteRepository.saveAndFlush(newCliente);
    }

    @Override
    public Cliente fromDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getCpf(), null,
                clienteDTO.getDataCriacao());
    }


}