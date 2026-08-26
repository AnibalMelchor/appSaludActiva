package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.service;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.CrearAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.DetalleAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.dto.ModificarAlimentoDto;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.Alimento;
import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.repository.AlimentoRepository;
import com.example.appsaludactiva.infra.exceptions.ValidacionException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Transactional
    public void crearAlimento(CrearAlimentoDto datos){
        Optional<Alimento> alimento = alimentoRepository.findByNombre(datos.nombre().toLowerCase());
        if (alimento.isPresent()){
            throw new ValidacionException("Ya existe un alimento registrado con el mismo nombre");
        }

        Alimento nuevoAlimento = new Alimento(datos);
        alimentoRepository.save(nuevoAlimento);

    }

    public List<DetalleAlimentoDto> obtenerTodosLosAlimentos() {
        return alimentoRepository.findAll()
                .stream()
                .map(DetalleAlimentoDto::new)
                .toList();
    }
    public DetalleAlimentoDto obtenerAlimento(Long id){
        Alimento alimento = alimentoRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un alimento con ese id"));
        return new DetalleAlimentoDto(alimento);
    }
    @Transactional
    public void modificarAlimento(ModificarAlimentoDto datos, Long id) {
        Alimento alimento = alimentoRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un alimento con ese id"));
        if (!alimento.isEstatus()){
            throw new ValidacionException("No puedes modificar un alimento desactivado");
        }
        alimento.modificarAlimento(datos);
        alimentoRepository.save(alimento);
    }
    @Transactional
    public void desactivarAlimento(Long id){
        Alimento alimento = alimentoRepository.findById(id).orElseThrow(() -> new ValidacionException("No existe un alimento con ese id"));
        if (!alimento.isEstatus()){
            throw new ValidacionException("No puedes descativar un alimento nuevamente");
        }
        alimento.desactivarAlimento();
        alimentoRepository.save(alimento);
    }
}
