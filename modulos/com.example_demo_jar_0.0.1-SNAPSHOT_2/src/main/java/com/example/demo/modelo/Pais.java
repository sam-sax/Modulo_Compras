package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "paises")
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    private boolean activo = true;

    @OneToMany(mappedBy = "pais")
    @JsonIgnore // Evita bucles infinitos en el JSON
    private List<Ciudad> ciudades;
    
    
            public Pais() {} // Constructor vacío necesario para JPA

        public Pais(Long id) {
            this.id = id;
        }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<Ciudad> getCiudades() { return ciudades; }
    public void setCiudades(List<Ciudad> ciudades) { this.ciudades = ciudades; }
}