package com.controle.financeiro.repository;

import com.controle.financeiro.model.Categoria;
import com.controle.financeiro.model.OrcamentoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrcamentoCategoriaRepository extends JpaRepository<OrcamentoCategoria, Long> {

    List<OrcamentoCategoria> findByUsuarioId(Long usuarioId);

    Optional<OrcamentoCategoria> findByUsuarioIdAndCategoria(Long usuarioId, Categoria categoria);
}
