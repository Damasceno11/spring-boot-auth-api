package com.example.crud_usuario_postgress.service;

import com.example.crud_usuario_postgress.dto.UsuarioRequestDTO;
import com.example.crud_usuario_postgress.dto.UsuarioResponseDTO;
import com.example.crud_usuario_postgress.exception.ResourceNotFoundException;
import com.example.crud_usuario_postgress.mapper.UsuarioMapper;
import com.example.crud_usuario_postgress.model.Usuario;
import com.example.crud_usuario_postgress.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;

    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAllWithRelationships()
                .stream()
                .map(usuarioMapper::toResponseDTO)// Converte cada Usuario para Usuario DTO
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdWithRelationships(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        return usuarioMapper.toResponseDTO(usuario);

    }

    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO requestDTO) {
        Usuario usuario = usuarioMapper.toEntity(requestDTO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
        usuario.setUsername(requestDTO.username());
        usuario.setPassword(requestDTO.password());

        // Futuramente atualizar endereços e produtos, logica mais complexa
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(Long id) {
      if (!usuarioRepository.existsById(id)){
          throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + id);
      }
      usuarioRepository.deleteById(id);

    }
}

