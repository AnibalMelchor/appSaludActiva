package com.example.appsaludactiva.domain.usuario.entity;

import com.example.appsaludactiva.domain.usuario.dto.ActualizarUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.RegistroUsuarioDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "credenciales")
@Entity(name = "Credenciales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Credenciales implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String usuario;
    private String contrasena;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;

    public Credenciales(RegistroUsuarioDto datos, String contrasenaCifrada) {
        this.usuario = datos.usuario();
        this.contrasena = contrasenaCifrada;
        this.activo = true;
        this.rol = Rol.USER;
    }
    public void actualizarCredenciales(ActualizarUsuarioDto datos,String contrasenaCifrada){
        if(datos.usuario() != null){
            this.usuario = datos.usuario();
        }
        if(datos.contrasena() != null){
            this.contrasena = contrasenaCifrada;
        }
    }
    public void desactivar(){
        this.activo = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.contrasena;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.activo;
    }
}
