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
    public void realizarDoacao(DoacaoDTO doacaoDTO) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.getReferenceById(doacaoDTO.acaoId());
        Doador doador = doadorRepository.findByCpf(doacaoDTO.cpf());

        if(doador == null) {
            doador = new Doador(doacaoDTO);
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
}
