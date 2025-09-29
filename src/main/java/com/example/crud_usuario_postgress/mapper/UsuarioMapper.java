package com.example.crud_usuario_postgress.mapper;

import com.example.crud_usuario_postgress.dto.EnderecoDTO;
import com.example.crud_usuario_postgress.dto.ProdutoDTO;
import com.example.crud_usuario_postgress.dto.UsuarioRequestDTO;
import com.example.crud_usuario_postgress.dto.UsuarioResponseDTO;
import com.example.crud_usuario_postgress.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponseDTO(Usuario usuario){
        // Concerter a lista de enderecos para uma de ENderecoDTO
        Set<EnderecoDTO> enderecosDTO = usuario.getEnderecos().stream().map(endereco -> new EnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        )).collect(Collectors.toSet());

        // Converte o conjunto de entidade Produto para um conjunto de ProdutoDTO
        Set<ProdutoDTO> produtosDTO = usuario.getProdutos().stream()
                .map(produto ->  new ProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getPreco()
                )).collect(Collectors.toSet());

        // Retorna o DTO completo com as listas aninhadas
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsername(),
                enderecosDTO,
                produtosDTO
        );
    }

    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setUsername(dto.username());
        usuario.setPassword(dto.password());
        return usuario;
    }
}
