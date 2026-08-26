package com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.repository;

import com.example.appsaludactiva.domain.alimentacion.catalogoAlimentos.entity.Alimento;
import org.hibernate.sql.ast.tree.expression.AliasedExpression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Optional<Alimento> findByNombre(String nombre);
}
