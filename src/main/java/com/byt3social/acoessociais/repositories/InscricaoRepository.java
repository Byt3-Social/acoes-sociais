package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    List<Inscricao> findByAcaoVoluntariado(AcaoVoluntariado acaoVoluntariado);
    @Query("select i.id as id, i.createdAt as createdAt, i.acaoVoluntariado.nomeAcao as acao from Inscricao i where i.participanteId = :colaboradorId")
    List<Map> findByParticipanteId(Integer colaboradorId, Sort sort);
    @Query("select i.acaoVoluntariado.id from Inscricao i where i.participanteId = :colaboradorId")
    List<Integer> buscarInscricoesPorAcaoId(Integer colaboradorId);
}
