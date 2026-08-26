package com.example.appsaludactiva.domain.rutina.registroDiario.entity;

import com.example.appsaludactiva.domain.rutina.catalogoEjercicios.entity.Ejercicio;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.ModificarRegistroEjercicioDto;
import com.example.appsaludactiva.domain.rutina.registroDiario.dto.RegistroEjercicioDto;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity(name = "RegistroEjercicio")
@Table(name = "registro_ejercicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RegistroEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilSalud perfilSalud;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id")
    private Ejercicio ejercicio;

    @Column(name = "duracion_minutos")
    private int duracionMinutos;

    @Column(name = "calorias_quemadas")
    private int  caloriasQuemadas;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public RegistroEjercicio(RegistroEjercicioDto datos, PerfilSalud usuario, Ejercicio ejercicio, int caloriasQuemadas) {
        this.perfilSalud = usuario;
        this.ejercicio = ejercicio;
        this.duracionMinutos = datos.duracionMinutos();
        this.caloriasQuemadas = caloriasQuemadas;
        this.fechaRegistro = LocalDateTime.now();

    }

    public void modificarDatos(ModificarRegistroEjercicioDto datos, Ejercicio ejercicio,int caloriasQuemadas) {
        this.ejercicio = ejercicio;
        this.duracionMinutos = datos.duracionMinutos();
        this.caloriasQuemadas = caloriasQuemadas;
    }
}
