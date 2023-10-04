package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
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

        PagseguroTransacaoDTO pagseguroTransacaoDTO = pagseguroService.processarPagamentoDoacao(doacaoDTO, doacao.getId());
        doacao.vincularTransacaoPagseguro(pagseguroTransacaoDTO.code());
        doacao.atualizarStatus(pagseguroTransacaoDTO);

        if(pagseguroTransacaoDTO.paymentLink() != null) {
            doacao.atualizarLinkPagamento(pagseguroTransacaoDTO.paymentLink());
        }
    }

    @Transactional
    public void processarNotificacao(String notificationCode) {
        PagseguroTransacaoDTO pagseguroTransacaoDTO = pagseguroService.consultarNotificacao(notificationCode);
        Doacao doacao = doacaoRepository.findById(Integer.parseInt(pagseguroTransacaoDTO.reference())).get();
        doacao.atualizarStatus(pagseguroTransacaoDTO);
    }

    public void cancelarDoacao(Integer doacaoID) {
        Doacao doacao = doacaoRepository.findById(doacaoID).get();

        pagseguroService.cancelarTransacao(doacao.getCodigo());
    }

    public void estornarDoacao(Integer doacaoID) {
        Doacao doacao = doacaoRepository.findById(doacaoID).get();

        pagseguroService.estornarTransacao(doacao.getCodigo());
    }
}
