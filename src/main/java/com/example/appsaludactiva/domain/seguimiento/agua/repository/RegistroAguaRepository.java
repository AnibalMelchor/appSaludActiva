package com.example.appsaludactiva.domain.seguimiento.agua.repository;

import com.example.appsaludactiva.domain.seguimiento.agua.entity.RegistroAgua;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RegistroAguaRepository extends JpaRepository<RegistroAgua, Long> {
    Optional<RegistroAgua> findByPerfilSaludIdAndFechaRegistro(Long perfilId, LocalDate fecha);
}
