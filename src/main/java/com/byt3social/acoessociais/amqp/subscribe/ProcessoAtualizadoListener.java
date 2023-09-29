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
public class ProcessoAtualizadoListener {
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Transactional
    @RabbitListener(queues = "processo.atualizado.acoes-sociais")
    public void processoAtualizado(@Payload OrganizacaoDTO organizacaoDTO) {
        Organizacao organizacao = organizacaoRepository.findByCadastroId(organizacaoDTO.id());

        organizacao.atualizarStatus(organizacaoDTO);
    }
}
