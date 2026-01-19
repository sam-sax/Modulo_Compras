package com.example.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "compradores")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoComprador; // Ej: COMP-001

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Usuario del sistema asignado a este comprador

    private LocalDateTime fechaCreacion;

    private boolean activo;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoComprador() { return codigoComprador; }
    public void setCodigoComprador(String codigoComprador) { this.codigoComprador = codigoComprador; }
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}