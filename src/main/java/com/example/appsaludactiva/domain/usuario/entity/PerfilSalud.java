package com.example.appsaludactiva.domain.usuario.entity;

import com.example.appsaludactiva.domain.usuario.dto.ActualizarUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.RegistroUsuarioDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Table(name = "perfiles_salud")
@Entity(name = "PerfilSalud")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PerfilSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long  id;

    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private double altura;
    private double peso;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credencial_id", unique = true)
    private Credenciales credencial;

    public PerfilSalud(RegistroUsuarioDto datos, Credenciales credencial) {
        this.id = null;
        this.nombre = datos.nombreCompleto();
        this.fechaNacimiento = datos.fechaNacimiento();
        this.altura = datos.altura();
        this.peso = datos.peso();
        this.credencial = credencial;
    }
    public void actualizarPefil(ActualizarUsuarioDto datos){
        if(datos.nombreCompleto() != null){
            this.nombre = datos.nombreCompleto();
        }
        if(datos.fechaNacimiento() != null){
            this.fechaNacimiento = datos.fechaNacimiento();
        }
        if(datos.altura() != null){
            this.altura = datos.altura();
        }
        if(datos.peso() != null){
            this.peso = datos.peso();
        }
    }
}
