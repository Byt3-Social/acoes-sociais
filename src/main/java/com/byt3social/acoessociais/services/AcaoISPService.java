package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.dto.AporteDTO;
import com.byt3social.acoessociais.models.*;
import com.byt3social.acoessociais.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class AcaoISPService {
    @Autowired
    private AcaoISPRepository acaoISPRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private IncentivoRepository incentivoRepository;
    @Autowired
    private LocalImpactadoRepository localImpactadoRepository;
    @Autowired
    private AporteRepository aporteRepository;

    public List<AcaoISP> consultarAcoesISP() {
        return acaoISPRepository.findAll();
    }

    public AcaoISP consultarAcaoISP(Integer acaoISPID) {
        return acaoISPRepository.findById(acaoISPID).get();
    }

    @Transactional
    public void cadastrarAcaoISP(AcaoISPDTO acaoISPDTO) {
        Categoria categoria = null;
        if(acaoISPDTO.categoria() != null) {
            categoria = categoriaRepository.getReferenceById(acaoISPDTO.categoria());
        }

        Area area = null;
        if(acaoISPDTO.area() != null) {
            area = areaRepository.getReferenceById(acaoISPDTO.area());
        }

        Incentivo incentivo = null;
        if(acaoISPDTO.incentivo() != null) {
            incentivo = incentivoRepository.getReferenceById(acaoISPDTO.incentivo());
        }

        AcaoISP acaoISP = new AcaoISP(acaoISPDTO, categoria, area, incentivo);
        acaoISP = acaoISPRepository.save(acaoISP);

        if(acaoISPDTO.locaisImpactados() != null) {
            List<LocalImpactado> locaisImpactados = new ArrayList<>();

            for(String localImpactado : acaoISPDTO.locaisImpactados()) {
                LocalImpactado novoLocalImpactado = new LocalImpactado(localImpactado, acaoISP);
                locaisImpactados.add(novoLocalImpactado);
            }

            localImpactadoRepository.saveAll(locaisImpactados);

            acaoISP.adicionarLocaisImpactados(locaisImpactados);
        }
    }

    @Transactional
    public void atualizarAcaoISP(Integer acaoISPID, AcaoISPDTO acaoISPDTO) {
        AcaoISP acaoISP = acaoISPRepository.findById(acaoISPID).get();

        Categoria categoria = null;
        if(acaoISPDTO.categoria() != null) {
            categoria = categoriaRepository.getReferenceById(acaoISPDTO.categoria());
        }

        Area area = null;
        if(acaoISPDTO.area() != null) {
            area = areaRepository.getReferenceById(acaoISPDTO.area());
        }

        Incentivo incentivo = null;
        if(acaoISPDTO.incentivo() != null) {
            incentivo = incentivoRepository.getReferenceById(acaoISPDTO.incentivo());
        }

        atualizarLocaisImpactados(acaoISPDTO, acaoISP);
        atualizarAportes(acaoISPDTO, acaoISP);

        acaoISP.atualizar(acaoISPDTO, categoria, incentivo, area);
    }

    private void atualizarAportes(AcaoISPDTO acaoISPDTO, AcaoISP acaoISP) {
        List<Aporte> aportes = acaoISP.getAportes();
        ListIterator<Aporte> aportesListIterator = aportes.listIterator();

        while(aportesListIterator.hasNext()) {
            Aporte aporte = aportesListIterator.next();

            Boolean contemAporte = acaoISPDTO.aportes().stream().anyMatch(aporteDTO -> aporteDTO.id() == aporte.getId());

            if(!contemAporte) {
                aportesListIterator.remove();
                aporteRepository.delete(aporte);
            }
        }

        for(AporteDTO aporteDTO : acaoISPDTO.aportes()) {
            Boolean existeAporte = aportes.stream().anyMatch(aporte -> aporte.getId().equals(aporteDTO.id()));

            if(!existeAporte) {
                Aporte aporte = new Aporte(aporteDTO, acaoISP);

                aporteRepository.save(aporte);
                aportes.add(aporte);
            }
        }
    }

    private void atualizarLocaisImpactados(AcaoISPDTO acaoISPDTO, AcaoISP acaoISP) {
        List<LocalImpactado> locaisImpactados = acaoISP.getLocaisImpactados();
        ListIterator<LocalImpactado> locaisImpactadosListIterator = locaisImpactados.listIterator();

        while(locaisImpactadosListIterator.hasNext()) {
            LocalImpactado localImpactado = locaisImpactadosListIterator.next();

            Boolean contemLocal = acaoISPDTO.locaisImpactados().contains(localImpactado.getLocal());

            if(!contemLocal) {
                locaisImpactadosListIterator.remove();
                localImpactadoRepository.delete(localImpactado);
            }
        }

        for(String local : acaoISPDTO.locaisImpactados()) {
            Boolean existeLocal = locaisImpactados.stream().anyMatch(localImpactado -> localImpactado.getLocal().equals(local));

            if(!existeLocal) {
                LocalImpactado novoLocalImpactado = new LocalImpactado(local, acaoISP);

                localImpactadoRepository.save(novoLocalImpactado);
                locaisImpactados.add(novoLocalImpactado);
            }
        }
    }

    @Transactional
    public void excluirAcaoISP(Integer acaoISPID) {
        AcaoISP acaoISP = acaoISPRepository.findById(acaoISPID).get();

        localImpactadoRepository.deleteAll(acaoISP.getLocaisImpactados());
        aporteRepository.deleteAll(acaoISP.getAportes());
        acaoISPRepository.deleteById(acaoISPID);
    }
}
