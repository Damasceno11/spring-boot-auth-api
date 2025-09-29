package com.example.crud_usuario_postgress.controller;

import com.example.crud_usuario_postgress.dto.LoginRequestDTO;
import com.example.crud_usuario_postgress.dto.LoginResponseDTO;
import com.example.crud_usuario_postgress.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/auth")
    @RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager aunthenticationManager;
        private final UserDetailsService userDetailsService;
        private final JwtService jwtService;

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
            // Autentica o usuário
            aunthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            // Se a autentificação for bem sucedida, carrega os detalhes do usuário
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

            // Gera o token JWT
            final String token = jwtService.generateToken(userDetails);

            // Retorna o token no corpo da resposta
            return  ResponseEntity.ok(new LoginResponseDTO(token));
        }

}
