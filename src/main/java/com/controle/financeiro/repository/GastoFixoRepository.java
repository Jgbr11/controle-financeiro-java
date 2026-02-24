package com.controle.financeiro.repository;

import com.controle.financeiro.model.GastoFixo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoFixoRepository extends JpaRepository<GastoFixo, Long> {

    List<GastoFixo> findByUsuarioId(Long usuarioId);

    List<GastoFixo> findByUsuarioIdAndAtivoTrue(Long usuarioId);
}
