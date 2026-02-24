package com.controle.financeiro.controller;

import com.controle.financeiro.dto.GastoDTO;
import com.controle.financeiro.model.Categoria;
import com.controle.financeiro.model.Gasto;
import com.controle.financeiro.model.User;
import com.controle.financeiro.service.GastoService;
import com.controle.financeiro.service.GastoFixoService;
import com.controle.financeiro.service.OrcamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;
    private final OrcamentoService orcamentoService;
    private final GastoFixoService gastoFixoService;

    // ===== PÁGINAS =====

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        // Lança gastos fixos do mês automaticamente
        gastoFixoService.lancarGastosFixosDoMes(user);

        model.addAttribute("totalSemana", gastoService.totalSemana(user.getId()));
        model.addAttribute("totalMes", gastoService.totalMes(user.getId()));
        model.addAttribute("mediaDiaria", gastoService.mediaDiaria(user.getId()));
        model.addAttribute("gastosPorCategoria", gastoService.gastosPorCategoriaMes(user.getId()));
        model.addAttribute("resumoSemanal", gastoService.resumoSemanal(user.getId()));
        model.addAttribute("resumoMensal", gastoService.resumoMensal(user.getId()));
        model.addAttribute("gastosRecentes", gastoService.listarTodos(user.getId()).stream().limit(5).toList());
        model.addAttribute("statusOrcamentos", orcamentoService.statusOrcamentos(user.getId()));
        model.addAttribute("nomeUsuario", user.getNome());
        return "dashboard";
    }

    @GetMapping("/gastos")
    public String gastosPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("gastos", gastoService.listarTodos(user.getId()));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("nomeUsuario", user.getNome());
        return "gastos";
    }

    @GetMapping("/gastos/semana")
    public String gastosSemana(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("gastos", gastoService.listarPorSemana(user.getId()));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("nomeUsuario", user.getNome());
        model.addAttribute("filtro", "semana");
        return "gastos";
    }

    @GetMapping("/gastos/mes")
    public String gastosMes(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("gastos", gastoService.listarPorMes(user.getId()));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("nomeUsuario", user.getNome());
        model.addAttribute("filtro", "mes");
        return "gastos";
    }

    // ===== API REST =====

    @GetMapping("/api/gastos")
    @ResponseBody
    public ResponseEntity<List<GastoDTO>> listarTodos(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(gastoService.listarTodos(user.getId()).stream().map(this::toDTO).toList());
    }

    @GetMapping("/api/gastos/{id}")
    @ResponseBody
    public ResponseEntity<GastoDTO> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return gastoService.buscarPorId(id)
                .filter(g -> g.getUsuario().getId().equals(user.getId()))
                .map(g -> ResponseEntity.ok(toDTO(g)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/gastos/semana")
    @ResponseBody
    public ResponseEntity<List<GastoDTO>> apiSemana(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(gastoService.listarPorSemana(user.getId()).stream().map(this::toDTO).toList());
    }

    @GetMapping("/api/gastos/mes")
    @ResponseBody
    public ResponseEntity<List<GastoDTO>> apiMes(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(gastoService.listarPorMes(user.getId()).stream().map(this::toDTO).toList());
    }

    @PostMapping("/api/gastos")
    @ResponseBody
    public ResponseEntity<GastoDTO> criar(@Valid @RequestBody GastoDTO dto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(toDTO(gastoService.salvar(dto, user)));
    }

    @PutMapping("/api/gastos/{id}")
    @ResponseBody
    public ResponseEntity<GastoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody GastoDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(toDTO(gastoService.atualizar(id, dto, user.getId())));
    }

    @DeleteMapping("/api/gastos/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal User user) {
        gastoService.deletar(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/gastos/resumo/semanal")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> resumoSemanal(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(gastoService.resumoSemanal(user.getId()));
    }

    @GetMapping("/api/gastos/resumo/mensal")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> resumoMensal(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(gastoService.resumoMensal(user.getId()));
    }

    private GastoDTO toDTO(Gasto g) {
        return new GastoDTO(g.getId(), g.getDescricao(), g.getValor(), g.getCategoria(), g.getData());
    }
}
