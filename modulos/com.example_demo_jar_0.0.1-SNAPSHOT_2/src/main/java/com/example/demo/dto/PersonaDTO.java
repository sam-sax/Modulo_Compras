package com.example.demo.dto;

import com.example.demo.modelo.TipoPersona;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class PersonaDTO {

    private Long idPersona;

    @NotNull(message = "El tipo de persona es obligatorio")
    private TipoPersona tipoPersona = TipoPersona.FISICA;

    @Size(max = 200, message = "Los nombres no pueden superar 200 caracteres")
    private String nombres;

    @Size(max = 200, message = "Los apellidos no pueden superar 200 caracteres")
    private String apellidos;

    @Size(max = 255, message = "La razón social no puede superar 255 caracteres")
    private String razonSocial;

    @Size(max = 255, message = "El representante legal no puede superar 255 caracteres")
    private String representanteLegal;

    private String telefono;
    private String direccion;
    
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Pattern(regexp = "^$|[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}", message = "Email inválido")
    private String email;

    private String estadoCivil;

    private Long ciudadId;
    private String ciudadNombre;

    // 🔑 Campo agregado para enviar el ID del país
    private Long paisId;
    private String paisNombre;

    private String numeroCedula;
    private String ruc;

    // Inicializados en false para evitar NULL en la DB
    private Boolean esProveedor = false;
    private Boolean esVendedor = false;
    private Boolean esComprador = false;
    private Boolean esCliente = false;

    // ===============================
    // Getters y Setters
    // ===============================
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

    public Long getCiudadId() { return ciudadId; }
    public void setCiudadId(Long ciudadId) { this.ciudadId = ciudadId; }

    public String getCiudadNombre() { return ciudadNombre; }
    public void setCiudadNombre(String ciudadNombre) { this.ciudadNombre = ciudadNombre; }

    public Long getPaisId() { return paisId; }
    public void setPaisId(Long paisId) { this.paisId = paisId; }

    public String getPaisNombre() { return paisNombre; }
    public void setPaisNombre(String paisNombre) { this.paisNombre = paisNombre; }

    public String getNumeroCedula() { return numeroCedula; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }

    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }

    public Boolean getEsProveedor() { return esProveedor; }
    public void setEsProveedor(Boolean esProveedor) { this.esProveedor = esProveedor; }

    public Boolean getEsVendedor() { return esVendedor; }
    public void setEsVendedor(Boolean esVendedor) { this.esVendedor = esVendedor; }

    public Boolean getEsComprador() { return esComprador; }
    public void setEsComprador(Boolean esComprador) { this.esComprador = esComprador; }

    public Boolean getEsCliente() { return esCliente; }
    public void setEsCliente(Boolean esCliente) { this.esCliente = esCliente; }
}
