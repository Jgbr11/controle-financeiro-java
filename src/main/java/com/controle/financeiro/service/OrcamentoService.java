package com.controle.financeiro.service;

import com.controle.financeiro.dto.OrcamentoCategoriaDTO;
import com.controle.financeiro.model.OrcamentoCategoria;
import com.controle.financeiro.model.User;
import com.controle.financeiro.repository.GastoRepository;
import com.controle.financeiro.repository.OrcamentoCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrcamentoService {

    private final OrcamentoCategoriaRepository orcamentoRepo;
    private final GastoRepository gastoRepo;

    public List<OrcamentoCategoria> listarPorUsuario(Long usuarioId) {
        return orcamentoRepo.findByUsuarioId(usuarioId);
    }

    public OrcamentoCategoria salvarOuAtualizar(OrcamentoCategoriaDTO dto, User usuario) {
        Optional<OrcamentoCategoria> existente = orcamentoRepo
                .findByUsuarioIdAndCategoria(usuario.getId(), dto.categoria());

        OrcamentoCategoria orc;
        if (existente.isPresent()) {
            orc = existente.get();
            orc.setLimite(dto.limite());
        } else {
            orc = OrcamentoCategoria.builder()
                    .categoria(dto.categoria())
                    .limite(dto.limite())
                    .usuario(usuario)
                    .build();
        }
        return orcamentoRepo.save(orc);
    }

    public void deletar(Long id, Long usuarioId) {
        OrcamentoCategoria orc = orcamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado"));
        if (!orc.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Orçamento não pertence ao usuário");
        }
        orcamentoRepo.delete(orc);
    }

    /**
     * Retorna o status de cada orçamento para o mês atual.
     * Cada item contém: categoria, descricaoCategoria, limite, gasto, percentual,
     * status
     */
    public List<Map<String, Object>> statusOrcamentos(Long usuarioId) {
        List<OrcamentoCategoria> orcamentos = orcamentoRepo.findByUsuarioId(usuarioId);
        if (orcamentos.isEmpty())
            return Collections.emptyList();

        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());

        // Busca os gastos agrupados por categoria no mês
        List<Object[]> gastosPorCategoria = gastoRepo.sumValorByCategoria(usuarioId, inicioMes, fimMes);
        Map<String, BigDecimal> mapGastos = new LinkedHashMap<>();
        for (Object[] row : gastosPorCategoria) {
            mapGastos.put(row[0].toString(), (BigDecimal) row[1]);
        }

        List<Map<String, Object>> resultado = new ArrayList<>();
        for (OrcamentoCategoria orc : orcamentos) {
            Map<String, Object> item = new LinkedHashMap<>();
            String catKey = orc.getCategoria().name();
            BigDecimal gasto = mapGastos.getOrDefault(catKey, BigDecimal.ZERO);
            BigDecimal limite = orc.getLimite();

            int percentual = 0;
            if (limite.compareTo(BigDecimal.ZERO) > 0) {
                percentual = gasto.multiply(BigDecimal.valueOf(100))
                        .divide(limite, 0, RoundingMode.HALF_UP)
                        .intValue();
            }

            String status;
            if (percentual >= 100) {
                status = "vermelho";
            } else if (percentual >= 75) {
                status = "amarelo";
            } else {
                status = "verde";
            }

            item.put("categoria", catKey);
            item.put("descricaoCategoria", orc.getCategoria().getDescricao());
            item.put("limite", limite);
            item.put("gasto", gasto);
            item.put("percentual", Math.min(percentual, 100));
            item.put("percentualReal", percentual);
            item.put("status", status);
            resultado.add(item);
        }

        return resultado;
    }
}
