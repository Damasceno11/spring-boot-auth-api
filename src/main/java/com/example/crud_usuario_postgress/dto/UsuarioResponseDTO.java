package com.example.crud_usuario_postgress.dto;

import java.util.List;
import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        String username,
        Set<EnderecoDTO> enderecos,
        Set<ProdutoDTO> produtos
) {}
