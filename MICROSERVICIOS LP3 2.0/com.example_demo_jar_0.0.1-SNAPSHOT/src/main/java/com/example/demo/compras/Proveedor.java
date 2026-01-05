package com.example.demo.compras;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "proveedores")
@Data
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 6, max = 15, message = "El RUC debe tener entre 6 y 15 caracteres")
    private String ruc;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Email(message = "Debe ingresar un correo válido")
    private String email;
}
