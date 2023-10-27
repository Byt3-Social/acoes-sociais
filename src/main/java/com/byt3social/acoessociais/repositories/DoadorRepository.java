package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Doador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoadorRepository extends JpaRepository<Doador, Integer> {
    Doador findByCpf(String cpf);
}
