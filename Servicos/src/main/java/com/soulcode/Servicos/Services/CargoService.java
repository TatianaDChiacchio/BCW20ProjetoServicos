package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Cargo;
import com.soulcode.Servicos.Repositories.CargoRepository;
import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    // primeiro serviço: mostrar todos os cargos cadastrados
    public List<Cargo> mostrarTodosCargos(){
        return cargoRepository.findAll();
    }

    public Cargo mostrarCargoPeloId(Integer idCargo){
        Optional<Cargo> cargo = cargoRepository.findById(idCargo);
        return cargo.orElseThrow();
    }

    public Cargo cadastrarCargo(Cargo cargo){
        //por precaução vamos limpar o id do Cargo
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    public Cargo editarCargo(Cargo cargo){
        return cargoRepository.save(cargo);
    }

    public void excluirCargo(Integer idCargo){
        try{
            cargoRepository.deleteById(idCargo);
        }catch(org.springframework.dao.DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Esse cargo não pode ser excluído porque existem funcionário(s) associado(s) a ele");
        }catch(org.springframework.dao.EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Cargo não cadastrado!");
    }


    }
}
