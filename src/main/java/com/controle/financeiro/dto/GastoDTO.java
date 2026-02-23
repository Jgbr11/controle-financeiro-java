package com.controle.financeiro.dto;

import com.controle.financeiro.model.Categoria;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record GastoDTO(
    Long id,
    @NotBlank(message = "Descrição é obrigatória")
    String descricao,
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    BigDecimal valor,
    @NotNull(message = "Categoria é obrigatória")
    Categoria categoria,
    @NotNull(message = "Data é obrigatória")
    LocalDate data
) {}
