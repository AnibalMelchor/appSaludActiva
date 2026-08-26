package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.repository;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EjerciciosRepository extends JpaRepository<Ejercicio, Long> {
    Optional<Ejercicio> findByNombre(String nombre);
}
