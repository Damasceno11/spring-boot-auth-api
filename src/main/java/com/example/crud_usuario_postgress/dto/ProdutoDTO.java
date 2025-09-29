package com.example.crud_usuario_postgress.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
        Long id,
        String nome,
        BigDecimal valor
) {
}
