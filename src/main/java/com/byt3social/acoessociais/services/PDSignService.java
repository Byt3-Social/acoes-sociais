package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PDSignService {
    @Value("${com.byt3social.pdsign.auth-url}")
    private String authUrl;
    @Value("${com.byt3social.pdsign.api-url}")
    private String apiUrl;
    @Value("${com.byt3social.pdsign.username}")
    private String username;
    @Value("${com.byt3social.pdsign.password}")
    private String password;
    @Value("${com.byt3social.pdsign.client-id}")
    private String clientId;
    @Value("${com.byt3social.pdsign.client-secret}")
    private String clientSecret;
    @Value("${com.byt3social.pdsign.grant-type}")
    private String grantType;
    @Value("${com.byt3social.pdsign.tenant-id}")
    private String tenantId;
    @Value("${com.byt3social.pdsign.requester-id}")
    private String requesterId;
    @Value("${com.byt3social.pdsign.company-id}")
    private String companyId;
    @Value("${com.byt3social.pdsign.action-type-id}")
    private String actionTypeId;
    @Value("${com.byt3social.pdsign.responsability-id}")
    private String responsibilityId;
    @Value("${com.byt3social.pdsign.authentication-type-id}")
    private String authenticationTypeId;

    private String login() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<String, String>();
        data.add("username", username);
        data.add("password", password);
        data.add("client_id", clientId);
        data.add("client_secret", clientSecret);
        data.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(data, headers);

        PDSignTokenDTO pdSignTokenDTO = restTemplate.postForObject(authUrl, request, PDSignTokenDTO.class);

        return pdSignTokenDTO.accessToken();
    }

    public String criarProcesso(ResponsavelDTO responsavelDTO) {
        String token = login();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("x-tenant", tenantId);

        Map<String, Object> companies = new HashMap<>();
        companies.put("id", companyId);

        Map<String, Object> representation = new HashMap<>();
        representation.put("willActAsPhysicalPerson", false);
        representation.put("willActRepresentingAnyCompany", true);
        representation.put("companies", Collections.singletonList(companies));

        Map<String, Object> authenticationType = new HashMap<>();
        authenticationType.put("id", authenticationTypeId);

        Map<String, Object> responsibility = new HashMap<>();
        responsibility.put("id", responsibilityId);

        Map<String, Object> actionType = new HashMap<>();
        actionType.put("id", actionTypeId);

        Map<String, Object> members = new HashMap<>();
        members.put("name", responsavelDTO.nome());
        members.put("email", responsavelDTO.email());
        members.put("documentType", "CPF");
        members.put("documentCode", responsavelDTO.cpf());
        members.put("actionType", actionType);
        members.put("responsibility", responsibility);
        members.put("authenticationType", authenticationType);
        members.put("type", "SUBSCRIBER");
        members.put("representation", representation);

        Map<String, Object> flow = new HashMap<>();
        flow.put("defineOrderOfInvolves", false);
        flow.put("hasExpiration", false);
        flow.put("readRequired", false);

        Map<String, Object> company = new HashMap<>();
        company.put("id", companyId);

        Map<String, Object> requester = new HashMap<>();
        requester.put("id", requesterId);

        Map<String, Object> body = new HashMap<>();
        body.put("title", "B3 Social | Contrato");
        body.put("requester", requester);
        body.put("company", company);
        body.put("flow", flow);
        body.put("members", Collections.singletonList(members));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        PDSignDTO pdSignDTO = restTemplate.postForObject(apiUrl + "/processes", request, PDSignDTO.class);

        return pdSignDTO.id();
    }

    public String criarDocumento(String processoId, String nomeDocumento) {
        String token = login();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("x-tenant", tenantId);

        Map<String, Object> body = new HashMap<>();
        body.put("extension", "PDF");
        body.put("isPendency", true);
        body.put("name", nomeDocumento);
        body.put("order", 0);
        body.put("type", "SIGN");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        PDSignDTO pdSignDTO = restTemplate.postForObject(apiUrl + "/processes/" + processoId + "/documents", request, PDSignDTO.class);

        return pdSignDTO.id();
    }

    public String uploadDocumento(MultipartFile file, String processoId, String documentoId) {
        String token = login();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(token);
        headers.set("x-tenant", tenantId);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<PDSignDTO> response = restTemplate.postForEntity(apiUrl + "/processes/" + processoId + "/documents/" + documentoId + "/upload", request, PDSignDTO.class);

        return response.getBody().id();
    }

    public String updateProcesso(String processoId) {
        String token = login();

        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("x-tenant", tenantId);

        Map<String, String> body = new HashMap<>();
        body.put("status", "RUNNING");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        PDSignDTO pdSignDTO = restTemplate.patchForObject(apiUrl + "/processes/" + processoId, request, PDSignDTO.class);

        return pdSignDTO.id();
    }

    public PDSignProcessosDTO buscarProcessosPDSign() {
        String token = login();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.set("x-tenant", tenantId);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PDSignProcessosDTO> pdSignProcessosDTO = restTemplate.exchange(apiUrl + "/processes", HttpMethod.GET, request, PDSignProcessosDTO.class);

        return pdSignProcessosDTO.getBody();
    }
}
