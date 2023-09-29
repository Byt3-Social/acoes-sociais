package com.byt3social.acoessociais.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ColaboradorCadastradoAMQPConfiguration {
    @Bean
    public Queue colaboradorCadastradoQueue() {
        return QueueBuilder.nonDurable("colaborador.cadastrado.acoes-sociais").build();
    }

    @Bean
    public FanoutExchange autenticacaoFantouExchange() {
        return ExchangeBuilder.fanoutExchange("autenticacao.ex").build();
    }

    @Bean
    public Binding bindColaboradorCadastradoToAutenticacao() {
        return BindingBuilder.bind(colaboradorCadastradoQueue()).to(autenticacaoFantouExchange());
    }
}
