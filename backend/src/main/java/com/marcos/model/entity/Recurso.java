package com.marcos.model.entity;

import com.marcos.model.enums.TipoRecurso;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recursos")
public class Recurso {

    @Id
    @SequenceGenerator(
            name = "recurso_seq",
            sequenceName = "recurso_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recurso_seq")
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRecurso tipoRecurso;

    private boolean activo = true;
}
