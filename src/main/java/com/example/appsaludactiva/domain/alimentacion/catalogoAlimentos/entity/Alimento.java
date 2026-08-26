package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.CrearAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.ModificarAlimentoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "calorias_por_porcion")
    private Integer caloriasPorcion;

    @Column(name = "tamano_porcion")
    private Double tamanoPorcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida")
    private UnidadMedida unidadMedida;

    private boolean estatus;

    public Alimento(CrearAlimentoDto datos) {
        this.nombre = datos.nombre().toLowerCase();
        this.caloriasPorcion = datos.caloriasPorcion();
        this.tamanoPorcion = datos.tamanoPorcion();
        this.unidadMedida = datos.unidadMedida();
        this.estatus = true;
    }

    public void modificarAlimento(ModificarAlimentoDto datos) {
        if (datos.nombre() != null){
            this.nombre = datos.nombre().toLowerCase();
        }
        if (datos.caloriasPorcion() != null){
            this.caloriasPorcion = datos.caloriasPorcion();
        }
        if (datos.tamanoPorcion() != null){
            this.tamanoPorcion = datos.tamanoPorcion();
        }
        if (datos.unidadMedida() != null){
            this.unidadMedida = datos.unidadMedida();
        }
    }

    public void desactivarAlimento() {
        this.estatus = false;
    }
}
