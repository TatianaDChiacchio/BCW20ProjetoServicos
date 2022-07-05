package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.EnderecoCliente;
import com.soulcode.Servicos.Repositories.ClienteRepository;
import com.soulcode.Servicos.Repositories.EnderecoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoClienteService {

    @Autowired
    EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<EnderecoCliente> mostrarTodosEnderecosCliente(){
        return enderecoClienteRepository.findAll();
    }

    public EnderecoCliente mostrarUmEnderecoPeloId(Integer idEnderecoCli){
        Optional<EnderecoCliente> endereco = enderecoClienteRepository.findById(idEnderecoCli);
        return endereco.orElseThrow();
    }

    // CADASTRO DE UM NOVO ENDEREÇO
    //regras 1 -> para cadastrar um endereço, o cliente já deve estar cadastrado no database
    //       2 -> no momento do cadastro do endereço, precisamos passar o id do cliente que é o dono desse endereço
    //       3 -> o id do endereço vai ser o mesmo id do cliente
    public EnderecoCliente cadastrarEnderecoDoCliente (EnderecoCliente enderecoCliente, Integer idCliente){
        enderecoCliente.setIdEndereco(idCliente);
        enderecoClienteRepository.save(enderecoCliente);

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        cliente.get().setEnderecoCliente(enderecoCliente);
        clienteRepository.save(cliente.get());
        return enderecoCliente;

//        Cliente cliente = clienteRepository.getById(idCliente);
//        cliente.setEnderecoCliente(enderecoCliente);
//        clienteRepository.save(cliente);
//        return enderecoCliente;
    }
}
