package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.AcaoISP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcaoISPRepository extends JpaRepository<AcaoISP, Integer> {
    List<AcaoISP> findByOrganizacaoId(Integer organizacaoId);
}
