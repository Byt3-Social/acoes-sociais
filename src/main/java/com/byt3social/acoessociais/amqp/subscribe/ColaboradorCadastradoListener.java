package com.byt3social.acoessociais.amqp.subscribe;

import com.byt3social.acoessociais.dto.UsuarioDTO;
import com.byt3social.acoessociais.models.Usuario;
import com.byt3social.acoessociais.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColaboradorCadastradoListener {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @RabbitListener(queues = "colaborador.cadastrado.acoes-sociais")
    public void colaboradorCadastrado(UsuarioDTO usuarioDTO) {
        Usuario colaborador = new Usuario(usuarioDTO);

        usuarioRepository.save(colaborador);
    }
}
