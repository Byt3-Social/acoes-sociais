package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface DoacaoRepository extends JpaRepository<Doacao, Integer> {
    @Query(value = "select count(d.id) as quantidade, sum(d.valor) as valor from Doacao as d where d.statusDoacao = 'PAID' and d.acaoVoluntariado = ?1")
    Map arrecadado(AcaoVoluntariado acaoVoluntariado);
    @Query(value = "select count(d.id) as quantidade, sum(d.valor) as valor from Doacao as d where d.statusDoacao = 'WAITING' and d.acaoVoluntariado = ?1")
    Map processado(AcaoVoluntariado acaoVoluntariado);
    @Query(value = "select count(d.id) as quantidade, sum(d.valor) as valor from Doacao as d where d.statusDoacao = 'CANCELED' and d.acaoVoluntariado = ?1")
    Map cancelado(AcaoVoluntariado acaoVoluntariado);
    @Query(value = "select count(d.id) as total, sum(d.valor) as valor, avg(d.valor) as media from Doacao as d")
    Map doacoes(AcaoVoluntariado acaoVoluntariado);
    @Query(value = "select d.metodoDoacao as metodo, count(d.id) as total, sum(d.valor) as valor, avg(d.valor) as media from Doacao as d where d.acaoVoluntariado = ?1 group by d.metodoDoacao")
    List<Map> doacoesPorMetodoDoacao(AcaoVoluntariado acaoVoluntariado);
    @Query(value = "select cast(d.createdAt as date) as x, sum(d.valor) as y from Doacao as d where d.acaoVoluntariado = ?1 group by cast(d.createdAt as date) order by cast(d.createdAt as date) asc")
    List<Map> doacoesPorDia(AcaoVoluntariado acaoVoluntariado);
    @Query("select d.acaoVoluntariado.nomeAcao as acao, d.valor as valor, d.createdAt as createdAt, d.link as link, d.statusDoacao as status from Doacao d where d.doador.usuarioId = :colaboradorId order by d.createdAt desc")
    List<Map> findByUsuarioId(Integer colaboradorId);
}
