package com.controle.financeiro.dto;

import com.controle.financeiro.model.Categoria;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record OrcamentoCategoriaDTO(
        Long id,
        @NotNull(message = "Categoria é obrigatória") Categoria categoria,
        @NotNull(message = "Limite é obrigatório") @Positive(message = "Limite deve ser positivo") BigDecimal limite) {
}
