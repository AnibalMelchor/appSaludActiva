package com.example.appsaludactiva.domain.seguimiento.sueno.repository;

import com.example.appsaludactiva.domain.seguimiento.sueno.entity.RegistroSueno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistroSuenoRepository extends JpaRepository<RegistroSueno, Long> {

    Optional<RegistroSueno> getReferenceByPerfilSaludId(Long id);
}
