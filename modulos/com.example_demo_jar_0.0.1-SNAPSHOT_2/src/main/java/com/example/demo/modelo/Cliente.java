package com.example.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "limite_credito")
    private Double limiteCredito = 0.0;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false, unique = true)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // Usuario que registró al cliente

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
    public Double getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(Double limiteCredito) { this.limiteCredito = limiteCredito; }
    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}