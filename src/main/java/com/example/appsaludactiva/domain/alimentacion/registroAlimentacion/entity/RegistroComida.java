package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity;

import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registros_comida")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegistroComida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilSalud perfilSalud;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "tipo_comida")
    @Enumerated(EnumType.STRING)
    private TipoComida tipoComida;

    @Column(name = "calorias_totales")
    private Double caloriasTotales;

    @OneToMany(mappedBy = "registroComida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleRegistroComida> detalles = new ArrayList<>();

    public RegistroComida(LocalDateTime fechaActual, TipoComida tipoComida,PerfilSalud usuario) {
        this.fechaRegistro = fechaActual;
        this.tipoComida = tipoComida;
        this.caloriasTotales = 0.0;
        this.perfilSalud = usuario;
    }

    public void agregarDetalle(DetalleRegistroComida detalle){
        this.detalles.add(detalle);
        detalle.setRegistroComida(this);
    }
}
