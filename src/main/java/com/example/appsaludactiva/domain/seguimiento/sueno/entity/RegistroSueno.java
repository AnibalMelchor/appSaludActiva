package com.example.appsaludactiva.domain.seguimiento.sueno.entity;

import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "registro_sueno")
@Entity(name = "RegistroSueno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegistroSueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilSalud perfilSalud;

    @Column(name = "horas_dormidas")
    private Double horasDormidas;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    public RegistroSueno(PerfilSalud usuario, double horasDormidas, LocalDate fechaActual) {
        this.perfilSalud = usuario;
        this.horasDormidas = horasDormidas;
        this.fechaRegistro = fechaActual;
    }
}
