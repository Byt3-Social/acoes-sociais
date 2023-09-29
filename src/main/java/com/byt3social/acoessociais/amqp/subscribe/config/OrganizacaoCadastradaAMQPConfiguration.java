package com.byt3social.acoessociais.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizacaoCadastradaAMQPConfiguration {
    @Bean
    public Queue organizacaoCadastradaQueue() {
        return QueueBuilder.nonDurable("organizacao.cadastrada.acoes-sociais").build();
    }

    @Bean
    public FanoutExchange prospeccaoFanoutExchange() {
        return ExchangeBuilder.fanoutExchange("prospeccao.ex").build();
    }

    @Bean
    public Binding bindOrganizacaoCadastradaToProspeccao() {
        return BindingBuilder.bind(organizacaoCadastradaQueue()).to(prospeccaoFanoutExchange());
    }
}
