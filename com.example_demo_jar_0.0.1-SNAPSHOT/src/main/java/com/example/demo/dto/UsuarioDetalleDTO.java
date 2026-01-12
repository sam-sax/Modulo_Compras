package com.example.demo.dto;

public class UsuarioDetalleDTO {
    private Long id;
    private String usuario;
    private String rol;
    private boolean estado;
    private String nombreCompletoPersona;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public String getNombreCompletoPersona() { return nombreCompletoPersona; }
    public void setNombreCompletoPersona(String nombre) { this.nombreCompletoPersona = nombre; }
}