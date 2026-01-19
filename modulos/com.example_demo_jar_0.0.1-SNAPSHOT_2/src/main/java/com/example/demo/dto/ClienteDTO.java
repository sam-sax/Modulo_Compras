package com.example.demo.dto;

import com.example.demo.modelo.TipoPersona;
import java.time.LocalDate;

public class ClienteDTO {
    // Campos de Persona
    private Long idPersona;
    private TipoPersona tipoPersona = TipoPersona.FISICA;
    private String nombres;
    private String apellidos;
    private String razonSocial;
    private String representanteLegal;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String email;
    private String estadoCivil;
    private String numeroCedula;
    private String ruc;
    private Long ciudadId;
    private Long paisId;

    // Campos específicos de Cliente (Igual que Vendedor)
    private Long idCliente;
    private Double limiteCredito = 0.0;
    private boolean activo = true;

    // --- Getters y Setters ---
    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public TipoPersona getTipoPersona() { return tipoPersona; }
    public void setTipoPersona(TipoPersona tipoPersona) { this.tipoPersona = tipoPersona; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getRepresentanteLegal() { return representanteLegal; }
    public void setRepresentanteLegal(String representanteLegal) { this.representanteLegal = representanteLegal; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }
    public String getNumeroCedula() { return numeroCedula; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public Long getCiudadId() { return ciudadId; }
    public void setCiudadId(Long ciudadId) { this.ciudadId = ciudadId; }
    public Long getPaisId() { return paisId; }
    public void setPaisId(Long paisId) { this.paisId = paisId; }
    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
    public Double getLimiteCredito() { return limiteCredito; }
    public void setLimiteCredito(Double limiteCredito) { this.limiteCredito = limiteCredito; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}