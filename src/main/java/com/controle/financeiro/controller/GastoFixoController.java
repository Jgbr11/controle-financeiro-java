package com.controle.financeiro.controller;

import com.controle.financeiro.dto.GastoFixoDTO;
import com.controle.financeiro.model.Categoria;
import com.controle.financeiro.model.GastoFixo;
import com.controle.financeiro.model.User;
import com.controle.financeiro.service.GastoFixoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GastoFixoController {

    private final GastoFixoService gastoFixoService;

    // ===== PÁGINA =====

    @GetMapping("/gastos-fixos")
    public String gastosFixosPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("gastosFixos", gastoFixoService.listarTodos(user.getId()));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("nomeUsuario", user.getNome());
        return "gastos-fixos";
    }

    // ===== API REST =====

    @GetMapping("/api/gastos-fixos")
    @ResponseBody
    public ResponseEntity<List<GastoFixoDTO>> listar(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                gastoFixoService.listarTodos(user.getId()).stream().map(this::toDTO).toList());
    }

    @PostMapping("/api/gastos-fixos")
    @ResponseBody
    public ResponseEntity<GastoFixoDTO> criar(@Valid @RequestBody GastoFixoDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(toDTO(gastoFixoService.salvar(dto, user)));
    }

    @PutMapping("/api/gastos-fixos/{id}")
    @ResponseBody
    public ResponseEntity<GastoFixoDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody GastoFixoDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(toDTO(gastoFixoService.atualizar(id, dto, user.getId())));
    }

    @DeleteMapping("/api/gastos-fixos/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal User user) {
        gastoFixoService.deletar(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    private GastoFixoDTO toDTO(GastoFixo gf) {
        return new GastoFixoDTO(gf.getId(), gf.getDescricao(), gf.getValor(),
                gf.getCategoria(), gf.getDiaVencimento(), gf.isAtivo());
    }
}
