package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
