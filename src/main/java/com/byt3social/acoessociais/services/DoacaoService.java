package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroCancelamentoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.enums.StatusDoacao;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.models.Doador;
import com.byt3social.acoessociais.repositories.AcaoVoluntariadoRepository;
import com.byt3social.acoessociais.repositories.DoacaoRepository;
import com.byt3social.acoessociais.repositories.DoadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoacaoService {
    @Autowired
    private PagseguroService pagseguroService;
    @Autowired
    private DoacaoRepository doacaoRepository;
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private DoadorRepository doadorRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public Doacao realizarDoacao(DoacaoDTO doacaoDTO, Integer colaboradorId) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.getReferenceById(doacaoDTO.acaoId());
        Doador doador = doadorRepository.findByCpf(doacaoDTO.cpf());

        if(doador == null) {
            doador = new Doador(doacaoDTO, colaboradorId);
            doadorRepository.save(doador);
        } else {
            doador.atualizar(doacaoDTO);
        }

        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        doacaoRepository.save(doacao);

        PagseguroTransacaoDTO pagseguroTransacaoDTO = pagseguroService.processarPagamentoDoacao(doacaoDTO, doacao.getId(), acaoVoluntariado);

        if(doacaoDTO.metodoDoacao() == MetodoDoacao.PIX) {
            doacao.vincularTransacaoPagseguro(pagseguroTransacaoDTO.id());
        } else {
            doacao.vincularTransacaoPagseguro(pagseguroTransacaoDTO.charges().get(0).id());
        }

        if(doacaoDTO.metodoDoacao() == MetodoDoacao.PIX) {
            doacao.atualizarStatus(StatusDoacao.WAITING);
            doacao.atualizarLinkPagamentoPix(pagseguroTransacaoDTO.qrCodes().get(0));
        } else if(doacaoDTO.metodoDoacao() == MetodoDoacao.BOLETO) {
            doacao.atualizarStatus(pagseguroTransacaoDTO.charges().get(0).status());
            doacao.atualizarLinkPagamentoBoleto(pagseguroTransacaoDTO.charges().get(0).links());
        } else {
            doacao.atualizarStatus(pagseguroTransacaoDTO.charges().get(0).status());
        }

        return doacao;
    }

    @Transactional
    public void processarNotificacao(PagseguroTransacaoDTO pagseguroTransacaoDTO) {
        Doacao doacao = doacaoRepository.findById(Integer.parseInt(pagseguroTransacaoDTO.referenceId())).get();
        doacao.atualizarStatus(pagseguroTransacaoDTO.charges().get(0).status());

        if(doacao.getMetodoDoacao() == MetodoDoacao.PIX) {
            doacao.atualizarIdentificador(pagseguroTransacaoDTO.charges().get(0).id());
        }

        if(pagseguroTransacaoDTO.charges().get(0).status() == StatusDoacao.PAID) {
            emailService.notificarDoacaoRecebida(doacao);
        }
    }

    @Transactional
    public void cancelarDoacao(Integer doacaoID) {
        Doacao doacao = doacaoRepository.findById(doacaoID).get();

        PagseguroCancelamentoDTO pagseguroCancelamentoDTO = pagseguroService.cancelarPagamento(doacao);

        doacao.cancelar(pagseguroCancelamentoDTO);
    }

    public Doacao consultarDoacao(Integer doacaoID) {
        return doacaoRepository.findById(doacaoID).get();
    }

    public Map gerarEstatisticas(Integer acaoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.getReferenceById(acaoID);

        Map<String, Object> estatisticas = new HashMap<>();
        estatisticas.put("arrecadado", doacaoRepository.arrecadado(acaoVoluntariado));
        estatisticas.put("processando", doacaoRepository.processado(acaoVoluntariado));
        estatisticas.put("cancelado", doacaoRepository.cancelado(acaoVoluntariado));
        estatisticas.put("doacoes", doacaoRepository.doacoes(acaoVoluntariado));
        estatisticas.put("doacoesPorMetodoDoacao", doacaoRepository.doacoesPorMetodoDoacao(acaoVoluntariado));
        estatisticas.put("doacoesPorDia", doacaoRepository.doacoesPorDia(acaoVoluntariado));

        return estatisticas;
    }

    public List<Map> consultarDoacoes(Integer colaboradorId) {
        List<Map> doacoes = doacaoRepository.findByUsuarioId(colaboradorId);

        return doacoes;
    }
}
