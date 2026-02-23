package com.controle.financeiro.service;

import com.controle.financeiro.dto.GastoDTO;
import com.controle.financeiro.model.Gasto;
import com.controle.financeiro.model.User;
import com.controle.financeiro.repository.GastoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GastoService {

    private final GastoRepository gastoRepository;

    public List<Gasto> listarTodos(Long usuarioId) {
        return gastoRepository.findByUsuarioIdOrderByDataDesc(usuarioId);
    }

    public Optional<Gasto> buscarPorId(Long id) {
        return gastoRepository.findById(id);
    }

    public Gasto salvar(GastoDTO dto, User usuario) {
        Gasto gasto = Gasto.builder()
                .descricao(dto.descricao())
                .valor(dto.valor())
                .categoria(dto.categoria())
                .data(dto.data())
                .usuario(usuario)
                .build();
        return gastoRepository.save(gasto);
    }

    public Gasto atualizar(Long id, GastoDTO dto, Long usuarioId) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não encontrado"));
        if (!gasto.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Gasto não pertence ao usuário");
        }
        gasto.setDescricao(dto.descricao());
        gasto.setValor(dto.valor());
        gasto.setCategoria(dto.categoria());
        gasto.setData(dto.data());
        return gastoRepository.save(gasto);
    }

    public void deletar(Long id, Long usuarioId) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não encontrado"));
        if (!gasto.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Gasto não pertence ao usuário");
        }
        gastoRepository.delete(gasto);
    }

    public List<Gasto> listarPorSemana(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        return gastoRepository.findByUsuarioIdAndDataBetweenOrderByDataDesc(
                usuarioId, hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
    }

    public List<Gasto> listarPorMes(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        return gastoRepository.findByUsuarioIdAndDataBetweenOrderByDataDesc(
                usuarioId,
                hoje.with(TemporalAdjusters.firstDayOfMonth()),
                hoje.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public BigDecimal totalSemana(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        return gastoRepository.sumValorByPeriodo(usuarioId,
                hoje.with(DayOfWeek.MONDAY), hoje.with(DayOfWeek.SUNDAY));
    }

    public BigDecimal totalMes(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        return gastoRepository.sumValorByPeriodo(usuarioId,
                hoje.with(TemporalAdjusters.firstDayOfMonth()),
                hoje.with(TemporalAdjusters.lastDayOfMonth()));
    }

    public BigDecimal mediaDiaria(Long usuarioId) {
        BigDecimal total = totalMes(usuarioId);
        int dia = LocalDate.now().getDayOfMonth();
        return dia > 0 ? total.divide(BigDecimal.valueOf(dia), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    public Map<String, BigDecimal> gastosPorCategoriaMes(Long usuarioId) {
        LocalDate hoje = LocalDate.now();
        List<Object[]> rows = gastoRepository.sumValorByCategoria(usuarioId,
                hoje.with(TemporalAdjusters.firstDayOfMonth()),
                hoje.with(TemporalAdjusters.lastDayOfMonth()));
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (Object[] r : rows)
            map.put(r[0].toString(), (BigDecimal) r[1]);
        return map;
    }

    public List<Map<String, Object>> resumoSemanal(Long usuarioId) {
        List<Map<String, Object>> lista = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        String[] labels = { "Semana Atual", "Semana -1", "Semana -2", "Semana -3" };
        for (int i = 0; i < 4; i++) {
            LocalDate ini = hoje.minusWeeks(i).with(DayOfWeek.MONDAY);
            LocalDate fim = hoje.minusWeeks(i).with(DayOfWeek.SUNDAY);
            BigDecimal total = gastoRepository.sumValorByPeriodo(usuarioId, ini, fim);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("label", labels[i]);
            item.put("total", total);
            lista.add(item);
        }
        Collections.reverse(lista);
        return lista;
    }

    public List<Map<String, Object>> resumoMensal(Long usuarioId) {
        List<Map<String, Object>> lista = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        String[] meses = { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
        for (int i = 5; i >= 0; i--) {
            LocalDate mes = hoje.minusMonths(i);
            LocalDate ini = mes.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate fim = mes.with(TemporalAdjusters.lastDayOfMonth());
            BigDecimal total = gastoRepository.sumValorByPeriodo(usuarioId, ini, fim);
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("label", meses[mes.getMonthValue() - 1] + "/" + mes.getYear());
            item.put("total", total);
            lista.add(item);
        }
        return lista;
    }
}
