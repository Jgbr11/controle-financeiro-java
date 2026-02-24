package com.controle.financeiro.controller;

import com.controle.financeiro.dto.OrcamentoCategoriaDTO;
import com.controle.financeiro.model.Categoria;
import com.controle.financeiro.model.OrcamentoCategoria;
import com.controle.financeiro.model.User;
import com.controle.financeiro.service.OrcamentoService;
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
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    // ===== PÁGINA =====

    @GetMapping("/orcamentos")
    public String orcamentosPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orcamentos", orcamentoService.listarPorUsuario(user.getId()));
        model.addAttribute("categorias", Categoria.values());
        model.addAttribute("nomeUsuario", user.getNome());
        return "orcamentos";
    }

    // ===== API REST =====

    @GetMapping("/api/orcamentos")
    @ResponseBody
    public ResponseEntity<List<OrcamentoCategoriaDTO>> listar(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                orcamentoService.listarPorUsuario(user.getId()).stream().map(this::toDTO).toList());
    }

    @PostMapping("/api/orcamentos")
    @ResponseBody
    public ResponseEntity<OrcamentoCategoriaDTO> salvar(@Valid @RequestBody OrcamentoCategoriaDTO dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(toDTO(orcamentoService.salvarOuAtualizar(dto, user)));
    }

    @DeleteMapping("/api/orcamentos/{id}")
    @ResponseBody
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal User user) {
        orcamentoService.deletar(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    private OrcamentoCategoriaDTO toDTO(OrcamentoCategoria o) {
        return new OrcamentoCategoriaDTO(o.getId(), o.getCategoria(), o.getLimite());
    }
}
