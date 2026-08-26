package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.Alimento;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.DetalleComidaDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_registro_comida")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DetalleRegistroComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double cantidad;

    @Column(name = "calorias_subtotales")
    private Double caloriasSubtotal;

    @ManyToOne
    @JoinColumn(name = "registro_comida_id")
    private RegistroComida registroComida;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;


    public DetalleRegistroComida(DetalleComidaDto detalle, Alimento alimento, Double caloriasSubtotal) {
        this.cantidad = detalle.cantidadConsumida();
        this.caloriasSubtotal = caloriasSubtotal;
        this.alimento = alimento;
    }
}
