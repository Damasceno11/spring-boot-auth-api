package com.example.crud_usuario_postgress.repository;

import com.example.crud_usuario_postgress.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.enderecos LEFT JOIN FETCH u.produtos")
    List<Usuario> findAllWithRelationships(); // Renomeado para maior clareza

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.enderecos LEFT JOIN FETCH u.produtos WHERE u.id = :id")
    Optional<Usuario> findByIdWithRelationships(Long id); // Buscar por ID com relacionamentos

    Optional<Usuario> findByUsername(String username);
}
