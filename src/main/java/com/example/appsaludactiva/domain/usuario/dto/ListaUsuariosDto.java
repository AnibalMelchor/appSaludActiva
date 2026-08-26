package com.example.appsaludactiva.domain.usuario.dto;

import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;

import java.time.LocalDate;

public record ListaUsuariosDto(
        Long id,
        String nombreCompleto,
        LocalDate fechaNacimiento,
        double altura,
        double peso,
        String usuario,
        String contrasena,
        boolean activo
) {
    public ListaUsuariosDto(PerfilSalud usuario) {
        this(usuario.getId(),usuario.getNombre(),usuario.getFechaNacimiento(),usuario.getAltura(),usuario.getPeso()
        ,usuario.getCredencial().getUsuario(),usuario.getCredencial().getContrasena(),usuario.getCredencial().isActivo());
    }
}
