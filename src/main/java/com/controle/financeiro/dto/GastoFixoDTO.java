package com.controle.financeiro.dto;

import com.controle.financeiro.model.Categoria;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record GastoFixoDTO(
        Long id,
        @NotBlank(message = "Descrição é obrigatória") String descricao,
        @NotNull(message = "Valor é obrigatório") @Positive(message = "Valor deve ser positivo") BigDecimal valor,
        @NotNull(message = "Categoria é obrigatória") Categoria categoria,
        @Min(value = 1, message = "Dia deve ser entre 1 e 31") @Max(value = 31, message = "Dia deve ser entre 1 e 31") int diaVencimento,
        boolean ativo) {
}
