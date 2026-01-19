package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CompradorDTO {

    @NotNull(message = "Debe seleccionar una persona")
    private Long personaId;

    @NotBlank(message = "Código de comprador es obligatorio")
    @Size(max = 20, message = "Código no puede superar 20 caracteres")
    private String codigoComprador;

    // Usuario que crea el comprador (Admin)
    private String creadoPor;

    // Getters y Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }
    public String getCodigoComprador() { return codigoComprador; }
    public void setCodigoComprador(String codigoComprador) { this.codigoComprador = codigoComprador; }
    public String getCreadoPor() { return creadoPor; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }
}