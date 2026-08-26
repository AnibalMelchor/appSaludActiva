package com.example.appsaludactiva.domain.seguimiento.agua.entity;

import com.example.appsaludactiva.domain.seguimiento.agua.dto.RegistroAguaDto;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "registro_agua")
@Entity(name = "RegistroAgua")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegistroAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilSalud perfilSalud;

    @Column(name = "vasos_tomados")
    private int vasosTomados;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;


    public RegistroAgua(RegistroAguaDto dto, PerfilSalud perfilSalud, LocalDate fechaActual) {
        this.perfilSalud = perfilSalud;
        this.vasosTomados = dto.cantidadVasos();
        this.fechaRegistro = fechaActual;
    }
}
