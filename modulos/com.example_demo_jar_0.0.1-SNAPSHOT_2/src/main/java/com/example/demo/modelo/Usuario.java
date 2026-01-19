package com.example.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String clave;

    @Column(nullable = false)
    private String rol;

    private boolean estado;

    private LocalDateTime fechaAlta;

    // --- CORRECCIÓN: Agregamos esta columna que ya tienes en tu MySQL ---
    @Column(name = "id_persona")
    private Long idPersona; 

    @OneToOne(mappedBy = "usuarioVinculado")
    private Persona persona;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public LocalDateTime getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDateTime fechaAlta) { this.fechaAlta = fechaAlta; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    // Getter y Setter para la columna nueva
    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }

    public Long getIdUsuario() { return id; }
}