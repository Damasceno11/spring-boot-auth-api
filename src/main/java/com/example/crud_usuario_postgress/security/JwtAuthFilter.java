package com.example.crud_usuario_postgress.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // Lombok cria construtor com os campos final
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Verifica se o cabeçalho Auth existe e se começa a "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response); // Se nõa, passa para o próximo filtro e encerra
            return;
        }

        // 2. Extrai o token JWT do cabeçalho
        jwt = authHeader.substring(7); // Remove "Bearer " do início

        // 3. Extrai o username do token JWT
        username = jwtService.extractUsername(jwt);

        // 4. Se o username existe e não há autenticação no contexto de segurança
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5. Verifica se o token é válido
            if (jwtService.isTokenValid(jwt, userDetails)){
                // Se o token for valido, cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Não precisamos da senha aqui
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 6. Atualiza o SecurityContext com a autenticação do usuário
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Passa a requisição para o próximo filtro na cadeia
        filterChain.doFilter(request, response);
    }
}
