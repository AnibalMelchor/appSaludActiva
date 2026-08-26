package com.example.appsaludactiva.domain.usuario.repository;

import com.example.appsaludactiva.domain.usuario.entity.Credenciales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface CredencialRepository extends JpaRepository<Credenciales,Long> {
    Optional<Credenciales> findByUsuario(String usuario);

}
