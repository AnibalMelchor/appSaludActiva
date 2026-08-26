package com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.service;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.Alimento;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.repository.AlimentoRepository;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.ActualizarRegistroComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.CrearRegistroComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.DetalleComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.dto.ListaRegistrosComidaDto;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.DetalleRegistroComida;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.entity.RegistroComida;
import com.example.appsaludactiva.domain.alimentacion.registroAlimentacion.repository.RegistroComidaRepository;
import com.example.appsaludactiva.domain.usuario.entity.PerfilSalud;
import com.example.appsaludactiva.domain.usuario.repository.PerfilSaludRepository;
import com.example.appsaludactiva.infra.exceptions.ConflictoConRecurso;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class RegistroComidaService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private RegistroComidaRepository comidaRepository;

    @Autowired
    private PerfilSaludRepository perfilSaludRepository;



    @Transactional
    public void registrarComida(CrearRegistroComidaDto datos,Long id){
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(()-> new ValidacionException("No existe un perfil con ese id"));
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDate fechaFiltracion = fechaActual.toLocalDate();

        LocalDateTime inicioDia = fechaFiltracion.atStartOfDay();
        LocalDateTime finDia = fechaFiltracion.atTime(LocalTime.MAX);
        boolean yaExisteComida = comidaRepository.existsByPerfilSaludIdAndTipoComidaAndFechaRegistroBetween(
                id,
                datos.tipoComida(),
                inicioDia,
                finDia
        );

        if (yaExisteComida) {
            throw new ConflictoConRecurso("Ya tienes un registro de " + datos.tipoComida() + " para el día de hoy.");
        }
        RegistroComida comida = new RegistroComida(fechaActual,datos.tipoComida(),usuario);

        double totalCalorias = 0.0;

        for (DetalleComidaDto detalle : datos.detalles()) {
            Alimento alimento = alimentoRepository.findById(detalle.alimentoId()).orElseThrow(() -> new ValidacionException("No existe un alimento con ese id"));
            if (!alimento.isEstatus()) {
                throw new ConflictoConRecurso("El alimento " + alimento.getNombre() + " está desactivado");
            }
            double caloriasSubtotal = detalle.cantidadConsumida() * alimento.getCaloriasPorcion();
            DetalleRegistroComida detalleRegistroComida = new DetalleRegistroComida(detalle, alimento, caloriasSubtotal);
            comida.agregarDetalle(detalleRegistroComida);
            totalCalorias += caloriasSubtotal;
        }
        comida.setCaloriasTotales(totalCalorias);
        comidaRepository.save(comida);
    }

    public List<ListaRegistrosComidaDto> obtenerRegistro(Long id, LocalDate fecha) {
        PerfilSalud usuario = perfilSaludRepository.findById(id).orElseThrow(()-> new ValidacionException("No existe un perfil con ese id"));
        LocalDate fechaFiltracion = (fecha != null) ? fecha : LocalDate.now();
        LocalDateTime inicioDia = fechaFiltracion.atStartOfDay();
        LocalDateTime finDia = fechaFiltracion.atTime(LocalTime.MAX);
        return comidaRepository
                .findByPerfilSaludIdAndFechaRegistroBetweenOrderByFechaRegistroDesc(usuario.getId(), inicioDia, finDia)
                .stream()
                .map(ListaRegistrosComidaDto::new)
                .toList();
    }

    @Transactional
    public void EliminarRegistro(Long perfilId,Long comidaId){
        RegistroComida comida = comidaRepository.findByIdAndPerfilSaludId(comidaId, perfilId)
                .orElseThrow(() -> new ValidacionException(
                        "No se encontró el registro de comida o no pertenece a este perfil"
                ));
        comidaRepository.delete(comida);
    }

    public ListaRegistrosComidaDto obtenerRegistroUnico(Long perfilId, Long comidaId) {
        RegistroComida comida = comidaRepository.findByIdAndPerfilSaludId(comidaId, perfilId)
                .orElseThrow(() -> new ValidacionException(
                        "No se encontró el registro de comida o no pertenece a este perfil"
                ));
        ListaRegistrosComidaDto registroUnico = new ListaRegistrosComidaDto(comida);
        return registroUnico;
    }

    @Transactional
    public void modificarRegistro(ActualizarRegistroComidaDto datos, Long perfilId, Long comidaId) {
        RegistroComida comida = comidaRepository.findByIdAndPerfilSaludId(comidaId, perfilId)
                .orElseThrow(() -> new ValidacionException(
                        "No se encontró el registro de comida o no pertenece a este perfil"
                ));
        LocalDate fechaRegistrada = comida.getFechaRegistro().toLocalDate();
        if (!fechaRegistrada.isEqual(LocalDate.now())){
            throw new ConflictoConRecurso("No puedes modificar un registro con una fecha anterior a hoy");
        }
        comida.getDetalles().clear();
        double totalCalorias = 0.0;
        for (DetalleComidaDto detalle : datos.detalles()) {
            Alimento alimento = alimentoRepository.findById(detalle.alimentoId()).orElseThrow(() -> new ValidacionException("No existe un alimento con ese id"));
            if (!alimento.isEstatus()) {
                throw new ConflictoConRecurso("El alimento " + alimento.getNombre() + " está desactivado");
            }
            double caloriasSubtotal = detalle.cantidadConsumida() * alimento.getCaloriasPorcion();
            DetalleRegistroComida detalleRegistroComida = new DetalleRegistroComida(detalle, alimento, caloriasSubtotal);
            comida.agregarDetalle(detalleRegistroComida);
            totalCalorias += caloriasSubtotal;
        }
        comida.setCaloriasTotales(totalCalorias);
        comidaRepository.save(comida);
    }
}
