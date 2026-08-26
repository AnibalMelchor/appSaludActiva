package com.example.appsaludactiva.domain.usuario.dto;

import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;

import java.time.LocalDate;

public record DetalleUsuarioDto(
        String nombreCompleto,
        LocalDate fechaNacimiento,
        Double altura,
        Double peso
) {
    public DetalleUsuarioDto(PerfilSalud usuario) {
        this(usuario.getNombre(),usuario.getFechaNacimiento(),usuario.getAltura(),usuario.getPeso());
    }
}
