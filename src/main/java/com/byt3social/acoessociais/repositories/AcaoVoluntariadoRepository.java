package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.AcaoVoluntariado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AcaoVoluntariadoRepository extends JpaRepository<AcaoVoluntariado, Integer> {
    @Query("select a from AcaoVoluntariado a where a.campanha = false and a.fase = 'EM_ANDAMENTO'")
    List<AcaoVoluntariado> buscarAcoesVoluntariadoEmAndamento();
    @Query("select a from AcaoVoluntariado a where a.campanha = true and a.fase = 'EM_ANDAMENTO'")
    List<AcaoVoluntariado> buscarAcoesDoacaoEmAndamento();
}
