package com.controle.financeiro.dto;

import jakarta.validation.constraints.*;

public record RegisterDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Informe um email válido (ex: usuario@dominio.com)") String email,
        @NotBlank(message = "Senha é obrigatória") @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres") @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=!*()\\-_]).{6,}$", message = "Senha deve conter pelo menos uma letra maiúscula e um caractere especial (@#$%&!*)") String senha) {
}
