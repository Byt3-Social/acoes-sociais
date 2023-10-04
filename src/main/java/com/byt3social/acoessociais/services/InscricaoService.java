package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.dto.ParticipanteDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.repositories.AcaoVoluntariadoRepository;
import com.byt3social.acoessociais.repositories.InscricaoRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class InscricaoService {
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private EmailService emailService;

    public void realizarInscricao(InscricaoDTO inscricaoDTO) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(inscricaoDTO.acaoId()).get();
        Inscricao inscricao = new Inscricao(inscricaoDTO.usuarioId(), acaoVoluntariado);

        inscricao = inscricaoRepository.save(inscricao);

        String QRCode = gerarQRCode(inscricao.getId());
        RestTemplate restTemplate = new RestTemplate();
        ParticipanteDTO inscrito = restTemplate.getForObject("http://localhost:8081/users/" + inscricao.getParticipanteId(), ParticipanteDTO.class);

        emailService.notificarInscricaoConfirmada(inscricao, inscrito, QRCode);
    }

    @Transactional
    public void cancelarInscricao(Integer inscricaoID) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoID).get();
        inscricao.cancelar();
    }

    private String gerarQRCode(Integer inscricaoID) {
        try {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix =
                    barcodeWriter.encode(inscricaoID.toString(), BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream pngQRCode = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngQRCode);
            byte[] pngData = pngQRCode.toByteArray();

            return Base64.getEncoder().encodeToString(pngData);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
