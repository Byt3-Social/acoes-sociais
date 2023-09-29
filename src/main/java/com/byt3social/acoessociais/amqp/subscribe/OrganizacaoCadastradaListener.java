package com.byt3social.acoessociais.amqp.subscribe;

import com.byt3social.acoessociais.dto.OrganizacaoDTO;
import com.byt3social.acoessociais.models.Organizacao;
import com.byt3social.acoessociais.repositories.OrganizacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrganizacaoCadastradaListener {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Transactional
    @RabbitListener(queues = "organizacao.cadastrada.acoes-sociais")
    public void organizacaoCadastrada(@Payload OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = new Organizacao(organizacaoDTO);

        organizacaoRepository.save(organizacao);
    }
}
