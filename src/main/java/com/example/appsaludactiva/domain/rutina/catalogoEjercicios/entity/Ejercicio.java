package com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.ActualizarEjercicioDto;
import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.dto.CrearEjercicioDto;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "ejercicios")
@Entity(name = "Ejercicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    private int caloriasPorMinuto;

    private boolean estatus;

    public Ejercicio(CrearEjercicioDto datos) {
        this.nombre = datos.nombreEjercicio();
        this.descripcion = datos.descripcion();
        this.caloriasPorMinuto = datos.caloriasPorMinuto();
        this.estatus = true;
    }
    public void ActualizarEjercicio(ActualizarEjercicioDto datos){
        this.nombre = datos.nombreEjercicio();
        this.descripcion = datos.descripcion();
        this.caloriasPorMinuto = datos.caloriasPorMinuto();
    }
    public void DesactivarEjercicio(){
        this.estatus = false;
    }
}
