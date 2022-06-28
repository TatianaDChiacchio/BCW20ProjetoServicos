package com.soulcode.Servicos.Repositories;

import com.soulcode.Servicos.Models.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado,Integer> {
}
