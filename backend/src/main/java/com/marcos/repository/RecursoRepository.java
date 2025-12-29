package com.marcos.repository;

import com.marcos.model.entity.Recurso;
import com.marcos.model.enums.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    Optional<Recurso> findByTipoRecurso (TipoRecurso tipo);

    boolean existsByNombre (String nombre);
}
