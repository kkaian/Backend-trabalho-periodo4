package com.backend.application.controller;

import com.backend.application.dto.LoginRequestDTO;
import com.backend.application.dto.LoginResponseDTO;
import com.backend.application.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);  // Utiliza o AuthService para realizar o login e gerar o JWT
    }
}
