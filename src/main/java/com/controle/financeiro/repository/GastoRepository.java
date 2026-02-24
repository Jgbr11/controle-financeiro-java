package com.controle.financeiro.repository;

import com.controle.financeiro.model.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {

        List<Gasto> findByUsuarioIdOrderByDataDesc(Long usuarioId);

        List<Gasto> findByUsuarioIdAndDataBetweenOrderByDataDesc(
                        Long usuarioId, LocalDate inicio, LocalDate fim);

        @Query("SELECT COALESCE(SUM(g.valor), 0) FROM Gasto g WHERE g.usuario.id = :uid AND g.data BETWEEN :ini AND :fim")
        BigDecimal sumValorByPeriodo(@Param("uid") Long usuarioId,
                        @Param("ini") LocalDate inicio,
                        @Param("fim") LocalDate fim);

        @Query("SELECT g.categoria, SUM(g.valor) FROM Gasto g WHERE g.usuario.id = :uid AND g.data BETWEEN :ini AND :fim GROUP BY g.categoria")
        List<Object[]> sumValorByCategoria(@Param("uid") Long usuarioId,
                        @Param("ini") LocalDate inicio,
                        @Param("fim") LocalDate fim);

        boolean existsByUsuarioIdAndDescricaoAndCategoriaAndDataBetween(
                        Long usuarioId, String descricao, com.controle.financeiro.model.Categoria categoria,
                        LocalDate inicio, LocalDate fim);
}
