package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.ParticipanteDTO;
import com.byt3social.acoessociais.exceptions.FailedToDeliverEmailException;
import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.models.Inscricao;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Locale;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("classpath:templates/email/inscricao-confirmada.html")
    private Resource resource;
    @Value("classpath:templates/email/doacao-recebida.html")
    private Resource doacaoRecebidaResource;

    public void notificarInscricaoConfirmada(Inscricao inscricao, ParticipanteDTO inscrito, String QRCode) {
        MimeMessage message = mailSender.createMimeMessage();

        Locale locale = new Locale.Builder().setLanguage("pt").setRegion("BR").build();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        String dataAcao = dateFormat.format(inscricao.getAcaoVoluntariado().getDataInicio());

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, inscrito.email());
            message.setSubject("B3 Social | Inscrição Confirmada");

            InputStream inputStream = resource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${nome_participante}", inscrito.nome());
            htmlTemplate = htmlTemplate.replace("${nome_acao_voluntariado}", inscricao.getAcaoVoluntariado().getNomeAcao());
            htmlTemplate = htmlTemplate.replace("${data_acao_voluntariado}", dataAcao);
            htmlTemplate = htmlTemplate.replace("${inscricao_qrcode}", QRCode);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedToDeliverEmailException();
        }
    }

    public void notificarDoacaoRecebida(Doacao doacao) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("byt3social@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, doacao.getDoador().getEmail());
            message.setSubject("B3 Social | Doação Recebida");

            InputStream inputStream = doacaoRecebidaResource.getInputStream();
            String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            htmlTemplate = htmlTemplate.replace("${nome_doador}", doacao.getDoador().getNome());
            htmlTemplate = htmlTemplate.replace("${valor_doacao}", String.format("%.2f", doacao.getValor()).replace(".", ","));

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            throw new FailedToDeliverEmailException();
        }
    }
}
