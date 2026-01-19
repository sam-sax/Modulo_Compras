package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VendedorDTO {

    @NotNull(message = "Debe seleccionar una persona")
    private Long personaId;

    @NotBlank(message = "Código de vendedor es obligatorio")
    @Size(max = 20, message = "Código de vendedor no puede superar 20 caracteres")
    private String codigoVendedor;

    // Usuario que crea el vendedor
    private String creadoPor;

    // Getters y Setters
    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public String getCodigoVendedor() { return codigoVendedor; }
    public void setCodigoVendedor(String codigoVendedor) { this.codigoVendedor = codigoVendedor; }

    public String getCreadoPor() { return creadoPor; }
    public void setCreadoPor(String creadoPor) { this.creadoPor = creadoPor; }
}
