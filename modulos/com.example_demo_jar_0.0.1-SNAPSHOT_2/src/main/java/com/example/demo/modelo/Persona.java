package com.example.demo.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "personas")
@Getter
@Setter
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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "usuario_asociado_id",
            referencedColumnName = "id_usuario",
            foreignKey = @ForeignKey(name = "FK_PERSONA_USUARIO_VINCULADO")
    )
    private Usuario usuarioVinculado;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @PrePersist
    protected void onCreate() {
        fechaIngreso = LocalDateTime.now();
    }

    // ===============================
    // Métodos utilitarios
    // ===============================

    public boolean esSinRolNiUsuario() {
        return !Boolean.TRUE.equals(esProveedor)
                && !Boolean.TRUE.equals(esCliente)
                && !Boolean.TRUE.equals(esVendedor)
                && !Boolean.TRUE.equals(esComprador)
                && usuarioVinculado == null;
    }

    @Transient
    public String getNombreMostrable() {
        if (this.tipoPersona == TipoPersona.JURIDICA
                && razonSocial != null
                && !razonSocial.isEmpty()) {
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
