package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroCancelamentoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Doacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PagseguroService {
    @Value("${com.byt3social.pagseguro.token}")
    private String token;
    @Value("${com.byt3social.pagseguro.pagamento-url}")
    private String pagamentoUrl;
    @Value("${com.byt3social.pagseguro.notification-url}")
    private String notificationUrl;
    @Value("${com.byt3social.pagseguro.cancelamento-url}")
    private String cancelUrl;

    public PagseguroTransacaoDTO processarPagamentoDoacao(DoacaoDTO doacaoDTO, Integer doacaoID, AcaoVoluntariado acaoVoluntariado) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, String> phones = new HashMap<>();
        phones.put("country", "55");
        phones.put("area", doacaoDTO.ddd());
        phones.put("number", doacaoDTO.telefone());
        phones.put("type", "MOBILE");

        Map<String, Object> customer = new HashMap<>();
        customer.put("name", doacaoDTO.nome());
        customer.put("email", doacaoDTO.email());
        customer.put("tax_id", doacaoDTO.cpf());
        customer.put("phones", Collections.singletonList(phones));

        Map<String, Object> items = new HashMap<>();
        items.put("reference_id", doacaoDTO.acaoId().toString());
        items.put("name", acaoVoluntariado.getNomeAcao());
        items.put("quantity", 1);
        items.put("unit_amount", Math.round(doacaoDTO.valorDoacao() * 100));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("reference_id", doacaoID.toString());
        requestBody.put("customer", customer);
        requestBody.put("items", Collections.singletonList(items));

        if(doacaoDTO.metodoDoacao() == MetodoDoacao.PIX) {
            Map<String, Object> value = new HashMap<>();
            value.put("value", Math.round(doacaoDTO.valorDoacao() * 100));

            Map<String, Object> amount = new HashMap<>();
            amount.put("amount", value);

            requestBody.put("qr_codes", Collections.singletonList(amount));
        } else if(doacaoDTO.metodoDoacao() == MetodoDoacao.CARTAO_CREDITO) {
            Map<String, Object> holder = new HashMap<>();
            holder.put("name", doacaoDTO.nome());

            Map<String, Object> card = new HashMap<>();
            card.put("encrypted", doacaoDTO.tokenCartao());
            card.put("security_code", doacaoDTO.cvv());
            card.put("holder", holder);
            card.put("store", false);

            Map<String, Object> paymentMethod = new HashMap<>();
            paymentMethod.put("type", "CREDIT_CARD");
            paymentMethod.put("installments", 1);
            paymentMethod.put("capture", true);
            paymentMethod.put("card", card);

            Map<String, Object> amount = new HashMap<>();
            amount.put("value", Math.round(doacaoDTO.valorDoacao() * 100));
            amount.put("currency", "BRL");

            Map<String, Object> charges = new HashMap<>();
            charges.put("reference_id", doacaoID.toString());
            charges.put("description", acaoVoluntariado.getNomeAcao());
            charges.put("amount", amount);
            charges.put("payment_method", paymentMethod);

            requestBody.put("charges", Collections.singletonList(charges));
        } else if(doacaoDTO.metodoDoacao() == MetodoDoacao.BOLETO) {
            Map<String, Object> address = new HashMap<>();
            address.put("country", "Brasil");
            address.put("region", doacaoDTO.endereco().estado());
            address.put("region_code", doacaoDTO.endereco().estado());
            address.put("city", doacaoDTO.endereco().cidade());
            address.put("postal_code", doacaoDTO.endereco().cep());
            address.put("street", doacaoDTO.endereco().endereco());
            address.put("number", doacaoDTO.endereco().numero());
            address.put("locality", doacaoDTO.endereco().bairro());

            Map<String, Object> holder = new HashMap<>();
            holder.put("name", doacaoDTO.nome());
            holder.put("tax_id", doacaoDTO.cpf());
            holder.put("email", doacaoDTO.email());
            holder.put("address", address);

            Map<String, String> instructionLines = new HashMap<>();
            instructionLines.put("line_1", "Pagamento processado via PagSeguro");

            LocalDate vencimentoBoleto = LocalDate.now();
            vencimentoBoleto = vencimentoBoleto.plusDays(7);

            Map<String, Object> boleto = new HashMap<>();
            boleto.put("due_date", vencimentoBoleto.toString());
            boleto.put("instruction_lines", instructionLines);
            boleto.put("holder", holder);

            Map<String, Object> paymentMethod = new HashMap<>();
            paymentMethod.put("type", "BOLETO");
            paymentMethod.put("boleto", boleto);

            Map<String, Object> amount = new HashMap<>();
            amount.put("value", Math.round(doacaoDTO.valorDoacao() * 100));
            amount.put("currency", "BRL");

            Map<String, Object> charges = new HashMap<>();
            charges.put("reference_id", doacaoID.toString());
            charges.put("description", acaoVoluntariado.getNomeAcao());
            charges.put("amount", amount);
            charges.put("payment_method", paymentMethod);

            requestBody.put("charges", Collections.singletonList(charges));
        }

        requestBody.put("notification_urls", Collections.singletonList(notificationUrl));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(pagamentoUrl, request, PagseguroTransacaoDTO.class);
    }

    public PagseguroCancelamentoDTO cancelarPagamento(Doacao doacao) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        Map<String, Object> value = new HashMap<>();
        value.put("value", Math.round(doacao.getValor() * 100));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", value);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject(cancelUrl + doacao.getCodigo() + "/cancel", entity, PagseguroCancelamentoDTO.class);
    }
}
