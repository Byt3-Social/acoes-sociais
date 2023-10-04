package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroSessionDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.exceptions.FailedToGenerateSessionIDException;
import com.byt3social.acoessociais.exceptions.FailedToProcessDonationResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

@Service
public class PagseguroService {
    @Value("${com.byt3social.pagseguro.session-url}")
    private String sessaoUrl;
    @Value("${com.byt3social.pagseguro.pagamento-url}")
    private String pagamentoUrl;
    @Value("${com.byt3social.pagseguro.notificacao-url}")
    private String notificacaoUrl;
    @Value("${com.byt3social.pagseguro.notificacao-parametros}")
    private String notificacaoParametros;
    @Value("${com.byt3social.pagseguro.notification-url}")
    private String notificationUrl;
    @Value("${com.byt3social.pagseguro.cancelamento-url}")
    private String cancelUrl;
    @Value("${com.byt3social.pagseguro.estorno-url}")
    private String refundUrl;

    public String gerarIDSessaoDoador() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(sessaoUrl, null, String.class);
        XmlMapper xmlMapper = new XmlMapper();

        try {
            PagseguroSessionDTO pagseguroSessionDTO = xmlMapper.readValue(result, PagseguroSessionDTO.class);

            return pagseguroSessionDTO.id();
        } catch (Exception e) {
            throw new FailedToGenerateSessionIDException();
        }
    }

    public PagseguroTransacaoDTO processarPagamentoDoacao(DoacaoDTO doacaoDTO, Integer doacaoID) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> body;

        if(doacaoDTO.metodoDoacao() == MetodoDoacao.CARTAO_CREDITO) {
            body = pagarCartaoCredito(doacaoDTO, doacaoID);
        } else {
            body = pagarBoleto(doacaoDTO, doacaoID);
        }

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        String resposta = restTemplate.postForObject(pagamentoUrl, entity, String.class);
        XmlMapper xmlMapper = new XmlMapper();

        System.out.println(resposta);

        try {
            PagseguroTransacaoDTO pagseguroTransacaoDTO = xmlMapper.readValue(resposta, PagseguroTransacaoDTO.class);

            System.out.println(pagseguroTransacaoDTO);

            return pagseguroTransacaoDTO;
        } catch (Exception e) {
            System.out.println(e);
            throw new FailedToProcessDonationResponse();
        }
    }

    private MultiValueMap<String, Object> pagarCartaoCredito(DoacaoDTO doacaoDTO, Integer doacaoID) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        body.add("paymentMode", "default");
        body.add("paymentMethod", converterMetodoDoacao(doacaoDTO.metodoDoacao()));
        body.add("currency", "BRL");
        body.add("reference", doacaoID.toString());
        body.add("senderHash", doacaoDTO.senderHash());
        body.add("senderName", doacaoDTO.nome());
        body.add("senderEmail", doacaoDTO.email());
        body.add("senderAreaCode", doacaoDTO.ddd());
        body.add("senderPhone", doacaoDTO.telefone());
        body.add("senderCPF", doacaoDTO.cpf());
        body.add("itemId1", doacaoDTO.acaoId());
        body.add("itemDescription1", doacaoDTO.descricao());
        body.add("itemAmount1", decimalFormat.format(doacaoDTO.valorDoacao()));
        body.add("itemQuantity1", 1);
        body.add("creditCardToken", doacaoDTO.tokenCartao());
        body.add("installmentQuantity", 1);
        body.add("installmentValue", decimalFormat.format(doacaoDTO.valorDoacao()));
        body.add("creditCardHolderName", doacaoDTO.nome());
        body.add("creditCardHolderCPF", doacaoDTO.cpf());
        body.add("creditCardHolderBirthDate", dateFormat.format(doacaoDTO.dataNascimento()));
        body.add("creditCardHolderAreaCode", doacaoDTO.ddd());
        body.add("creditCardHolderPhone", doacaoDTO.telefone());
        body.add("billingAddressStreet", doacaoDTO.endereco().endereco());
        body.add("billingAddressNumber", doacaoDTO.endereco().numero());
        body.add("billingAddressDistrict", doacaoDTO.endereco().bairro());
        body.add("billingAddressComplement", doacaoDTO.endereco().complemento());
        body.add("billingAddressCity", doacaoDTO.endereco().cidade());
        body.add("billingAddressState", doacaoDTO.endereco().estado());
        body.add("billingAddressPostalCode", doacaoDTO.endereco().cep());
        body.add("billingAddressCountry", "BRA");
        body.add("shippingAddressRequired", "false");
        body.add("notificationURL", notificationUrl);
        return body;
    }

    private MultiValueMap<String, Object> pagarBoleto(DoacaoDTO doacaoDTO, Integer doacaoID) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        body.add("paymentMode", "default");
        body.add("paymentMethod", converterMetodoDoacao(doacaoDTO.metodoDoacao()));
        body.add("currency", "BRL");
        body.add("extraAmount", "0.00");
        body.add("reference", doacaoID.toString());
        body.add("senderHash", doacaoDTO.senderHash());
        body.add("senderName", doacaoDTO.nome());
        body.add("senderEmail", doacaoDTO.email());
        body.add("senderAreaCode", doacaoDTO.ddd());
        body.add("senderPhone", doacaoDTO.telefone());
        body.add("senderCPF", doacaoDTO.cpf());
        body.add("itemId1", doacaoDTO.acaoId());
        body.add("itemDescription1", doacaoDTO.descricao());
        body.add("itemAmount1", decimalFormat.format(doacaoDTO.valorDoacao()));
        body.add("itemQuantity1", 1);
        body.add("shippingAddressRequired", "false");
        body.add("notificationURL", notificationUrl);
        return body;
    }

    private String converterMetodoDoacao(MetodoDoacao metodoDoacao) {
        if(metodoDoacao.equals(MetodoDoacao.BOLETO)) {
            return "boleto";
        } else if(metodoDoacao.equals(MetodoDoacao.CARTAO_CREDITO)) {
            return "creditCard";
        } else {
            return "eft";
        }
    }

    public PagseguroTransacaoDTO consultarNotificacao(String notificationCode) {
        String urlNotificacao = notificacaoUrl + notificationCode + notificacaoParametros;

        RestTemplate restTemplate = new RestTemplate();
        String resposta = restTemplate.getForObject(urlNotificacao, String.class);

        XmlMapper xmlMapper = new XmlMapper();

        try {
            PagseguroTransacaoDTO pagseguroTransacaoDTO = xmlMapper.readValue(resposta, PagseguroTransacaoDTO.class);

            System.out.println(pagseguroTransacaoDTO);

            return pagseguroTransacaoDTO;
        } catch (Exception e) {
            throw new FailedToProcessDonationResponse();
        }
    }

    public void cancelarTransacao(String codigo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.ALL));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("transactionCode", codigo);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.postForObject(cancelUrl, entity, Void.class);
    }

    public void estornarTransacao(String codigo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.ALL));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("transactionCode", codigo);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        restTemplate.postForObject(refundUrl, entity, Void.class);
    }
}
