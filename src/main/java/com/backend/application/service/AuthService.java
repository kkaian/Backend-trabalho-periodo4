package com.backend.application.service;

import com.backend.application.dto.LoginRequestDTO;
import com.backend.application.dto.LoginResponseDTO;
import com.backend.application.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        // Verificação de credenciais
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
        );

        // Gerar token JWT
        String token = jwtUtils.generateToken(authentication.getName()); 

        // Retornar token
        return new LoginResponseDTO(token);
    }
}
