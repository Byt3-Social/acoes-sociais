package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Interesse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteresseRepository extends JpaRepository<Interesse, Integer> {
    List<Interesse> findByUsuarioId(Integer usuarioId);
    @Query("select i.segmento.id from Interesse i where i.usuarioId = :usuarioId")
    List<Integer> findIdByUsuarioId(Integer usuarioId);
}
