package com.runasimi_edu.backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "grados")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Integer orden;

    @OneToMany(mappedBy = "grado", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "grado", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Actividad> actividades;

    public Grado(){

    }

    public Grado(List<Actividad> actividades, String descripcion, Long id, String nombre, Integer orden, List<Usuario> usuarios) {
        this.actividades = actividades;
        this.descripcion = descripcion;
        this.id = id;
        this.nombre = nombre;
        this.orden = orden;
        this.usuarios = usuarios;
    }

}