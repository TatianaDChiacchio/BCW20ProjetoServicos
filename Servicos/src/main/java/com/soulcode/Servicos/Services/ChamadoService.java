package com.soulcode.Servicos.Services;

import com.soulcode.Servicos.Models.Chamado;
import com.soulcode.Servicos.Repositories.ChamadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    ChamadoRepository chamadoRepository;

    // findAll (método da Spring Data) - busca todos os registros
    public List<Chamado> mostrarTodosChamados(){
        return chamadoRepository.findAll();	}

    // findById - busca um registro pela sua chave primária
    public Chamado mostrarUmChamado(Integer idChamado) {
        Optional<Chamado> chamado = chamadoRepository.findById(idChamado);
        return chamado.orElseThrow();
    }
}
