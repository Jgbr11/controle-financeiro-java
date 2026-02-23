package com.controle.financeiro.controller;

import com.controle.financeiro.dto.LoginDTO;
import com.controle.financeiro.dto.RegisterDTO;
import com.controle.financeiro.dto.TokenDTO;
import com.controle.financeiro.model.User;
import com.controle.financeiro.security.JwtService;
import com.controle.financeiro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    // ===== VIEWS =====

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha) {
        // Validate email format
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            return "redirect:/register?error=email_invalido";
        }
        // Validate password: at least 6 chars, 1 uppercase, 1 special character
        if (senha.length() < 6 || !senha.matches(".*[A-Z].*") || !senha.matches(".*[@#$%^&+=!*()\\-_].*")) {
            return "redirect:/register?error=senha_fraca";
        }
        try {
            userService.registrar(new RegisterDTO(nome, email, senha));
            return "redirect:/login?registered=true";
        } catch (RuntimeException e) {
            return "redirect:/register?error=email_existente";
        }
    }

    // ===== API REST =====

    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<TokenDTO> apiLogin(@Valid @RequestBody LoginDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
        String token = jwtService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/api/auth/register")
    @ResponseBody
    public ResponseEntity<?> apiRegister(@Valid @RequestBody RegisterDTO dto) {
        try {
            User user = userService.registrar(dto);
            return ResponseEntity.ok(Map.of(
                    "message", "Usuário registrado com sucesso",
                    "nome", user.getNome(),
                    "email", user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
