package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Models.Funcionario;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Repositories.FuncionarioRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// quando se fala em serviços, estamos falando dos métodos do crud da tabela

@Service
public class FuncionarioService {

    // aqui se faz a injeção de dependência
    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    //primeiro serviço na tabela de funcionário vai ser a leitura de todos
    //os funcionários cadastrados
    //findAll -> método do spring Data JPA -> busca todos os registros de uma tabela
    public List<Funcionario> mostrarTodosFuncionarios(){

        return funcionarioRepository.findAll();
    }


    //vamos mais um serviço relacionado ao funcionário
    //criar um serviço de buscar apenas um funcionário pelo seu id(chave primária)

    public Funcionario mostrarUmFuncionarioPeloId(Integer idFuncionario)
    {
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        return funcionario.orElseThrow(
                () -> new EntityNotFoundException("Funcionário não cadastrado: " + idFuncionario)
        );
    }

    //vamos criar mais um serviço pra buscar um funcionário pelo seu email
    public Funcionario mostrarUmFuncionarioPeloEmail(String email){
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        return funcionario.orElseThrow();
    }

    public List<Funcionario> mostrarTodosFuncionariosDeUmCargo(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return funcionarioRepository.findByCargo(cargo);
    }

    //vamos criar um serviço para cadastrar um novo funcionário
    public Funcionario cadastrarFuncionario(Funcionario funcionario, Integer idCargo) {

        //if(funcionarioRepository.findByEmail(funcionario.getEmail()) == null){
        try{
            //só por precaução nós vamos colocar o id do funcionário como nullo
            funcionario.setIdFuncionario(null);
            Optional<Cargo> cargo = cargoRepository.findById(idCargo);
            funcionario.setCargo(cargo.get());
            return funcionarioRepository.save(funcionario);
        }catch(org.springframework.dao.DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Já existe um fucionário com esse email");
        }





    }

    public void excluirFuncionario(Integer idFuncionario){
        //mostrarUmFuncionarioPeloId(idFuncionario);
        Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
        if(funcionario.isPresent()) {
            funcionarioRepository.deleteById(idFuncionario);
        }else{
            throw new EntityNotFoundException("Funcionário não cadastrado: " + idFuncionario);
        }
    }
    public Funcionario editarFuncionario(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto){
        Funcionario funcionario = mostrarUmFuncionarioPeloId(idFuncionario);
        funcionario.setFoto(caminhoFoto);
        return funcionarioRepository.save(funcionario);
    }
}
