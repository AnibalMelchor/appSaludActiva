package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.repository;

import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.RegistroComida;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.TipoComida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistroComidaRepository extends JpaRepository<RegistroComida, Long> {
    List<RegistroComida> findByPerfilSaludIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(Long perfilId, LocalDateTime inicio,
                                                                    LocalDateTime fin);
    Optional<RegistroComida> findByIdAndPerfilSaludId(Long comidaId, Long perfilId);

    boolean existsByPerfilSaludIdAndTipoComidaAndFechaRegistroBetween(
            Long perfilId,
            TipoComida tipoComida,
            LocalDateTime inicio,
            LocalDateTime fin
    );
}
