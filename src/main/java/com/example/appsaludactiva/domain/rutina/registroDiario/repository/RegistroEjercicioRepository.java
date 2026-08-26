package com.example.appsaludactiva.domain.rutina.registroDiario.repository;

import com.example.appsaludactiva.domain.rutina.registroDiario.entity.RegistroEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistroEjercicioRepository extends JpaRepository<RegistroEjercicio, Long> {
    List<RegistroEjercicio> findByPerfilSaludIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(
            Long perfilId,
            LocalDateTime inicioDia,
            LocalDateTime finDia
    );
}
