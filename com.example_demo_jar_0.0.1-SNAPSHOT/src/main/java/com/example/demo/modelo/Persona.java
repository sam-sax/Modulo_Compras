package com.example.demo.modelo;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_persona", nullable = false)
    private TipoPersona tipoPersona = TipoPersona.FISICA;

    @Column(name = "nombres", length = 200)
    private String nombres;

    @Column(name = "apellidos", length = 200)
    private String apellidos;

    @Column(name = "razon_social", length = 255)
    private String razonSocial;

    @Column(name = "representante_legal", length = 255)
    private String representanteLegal;

    private String telefono;
    private String direccion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    private String email;
    private String estadoCivil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ciudad_id", nullable = true)
    private Ciudad ciudad;

    @Column(name = "numero_cedula", length = 50)
    private String numeroCedula;

    @Column(name = "ruc", length = 50)
    private String ruc;

    @Column(name = "es_proveedor")
    private Boolean esProveedor = false;

    @Column(name = "es_vendedor")
    private Boolean esVendedor = false;

    @Column(name = "es_comprador")
    private Boolean esComprador = false;

    @Column(name = "es_cliente")
    private Boolean esCliente = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

        // 
     @OneToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "usuario_asociado_id", referencedColumnName = "id_usuario")
     private Usuario usuarioVinculado;

    @PrePersist
    protected void onCreate() {
        fechaIngreso = LocalDateTime.now();
    }

    // ===============================
    // Getters y Setters (omitidos aquí por brevedad, mantenelos)
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

    public LocalDateTime getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDateTime fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEstadoCivil() { return estadoCivil; }
    public void setEstadoCivil(String estadoCivil) { this.estadoCivil = estadoCivil; }

    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }

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

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Usuario getUsuarioVinculado() { return usuarioVinculado; }
    public void setUsuarioVinculado(Usuario usuarioVinculado) { this.usuarioVinculado = usuarioVinculado; }

    // ===============================
    // Método utilitario seguro para la vista
    // ===============================
    /**
     * Devuelve true si la persona NO tiene ninguno de los roles y además no
     * tiene usuario vinculado. Usa Boolean.TRUE.equals(...) para evitar NPE.
     */
    public boolean esSinRolNiUsuario() {
        return !Boolean.TRUE.equals(esProveedor)
                && !Boolean.TRUE.equals(esCliente)
                && !Boolean.TRUE.equals(esVendedor)
                && !Boolean.TRUE.equals(esComprador)
                && usuarioVinculado == null;
    }
    

            @Transient
            public String getNombreMostrable() {
                if (this.tipoPersona == TipoPersona.JURIDICA && razonSocial != null && !razonSocial.isEmpty()) {
                    return razonSocial;
                }
                String nom = (nombres != null) ? nombres : "";
                String ape = (apellidos != null) ? apellidos : "";
                String completo = (nom + " " + ape).trim();
                return completo.isEmpty() ? "SIN NOMBRE" : completo;
            }

            @Transient
            public String getDocumentoMostrable() {
                if (ruc != null && !ruc.isEmpty()) return ruc;
                if (numeroCedula != null && !numeroCedula.isEmpty()) return numeroCedula;
                return "S/D";
            }
}
