package com.example.appsaludactiva.domain.usuario.service;

import com.example.appsaludactiva.domain.usuario.dto.ActualizarUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.DetalleUsuarioDto;
import com.example.appsaludactiva.domain.usuario.dto.ListaUsuariosDto;
import com.example.appsaludactiva.domain.usuario.dto.RegistroUsuarioDto;
import com.example.appsaludactiva.domain.usuario.entity.Credenciales;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import com.example.appsaludactiva.domain.usuario.repository.CredencialRepository;
import com.example.appsaludactiva.domain.usuario.repository.PerfilSaludRepository;
import com.example.appsaludactiva.infra.exceptions.ConflictoConRecurso;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private PerfilSaludRepository perfilSaludRepository;

    @Autowired
    private CredencialRepository credencialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DetalleUsuarioDto detalleUsuarios(Long id){
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un perfil con ese ID"));

        DetalleUsuarioDto detalleUsurio = new DetalleUsuarioDto(usuario);
        return detalleUsurio;
    }

    public List<ListaUsuariosDto> listarUsuarios(){
        List<ListaUsuariosDto> listaUsuarios = new ArrayList<>();
        perfilSaludRepository.findAll().forEach(perfilSalud -> {
            listaUsuarios.add(new ListaUsuariosDto(perfilSalud));
        });
        if (listaUsuarios.isEmpty()){
            throw new ValidacionException("No hay ningun usuario registrado");
        }
        return listaUsuarios;
    }

    @Transactional
    public void registrarUsuario(RegistroUsuarioDto datos) {

        var usuarioUnico = credencialRepository.findByUsuario(datos.usuario());

        if (usuarioUnico.isPresent()){
            throw new ConflictoConRecurso("Ya existe un usuario con ese usuario, favor de colocar uno diferente");
        }

        String contrasenaCifrada = passwordEncoder.encode(datos.contrasena());

        var credencial = new Credenciales(datos, contrasenaCifrada);
        var perfil = new PerfilSalud(datos,credencial);
        perfilSaludRepository.save(perfil);
    }

    @Transactional
    public void actualizarUsuario(Long id,ActualizarUsuarioDto datos){
        var usuarioExistente = perfilSaludRepository.findById(id);
        if(usuarioExistente.isPresent()){
            PerfilSalud usuarioModificado = usuarioExistente.get();
            usuarioModificado.actualizarPefil(datos);
            Credenciales credenciales = usuarioModificado.getCredencial();
            String contrasenaCifrada = passwordEncoder.encode(datos.contrasena());
            credenciales.actualizarCredenciales(datos,contrasenaCifrada);
        }else{
            throw new ValidacionException("No existe un perfil con ese usuario");
        }
    }
    @Transactional
    public void desactivarUsario(Long id){
        var usuarioExistente = perfilSaludRepository.findById(id);
        if(usuarioExistente.isPresent()){
            PerfilSalud usuarioDesactivado = usuarioExistente.get();
            Credenciales credenciales = usuarioDesactivado.getCredencial();
            if (!credenciales.isActivo()){
                throw new ConflictoConRecurso("Ya se encuentra desactivado este perfil");
            }
            credenciales.desactivar();
        }else{
            throw new ValidacionException("No existe un perfil con ese usuario");
        }
    }
}
