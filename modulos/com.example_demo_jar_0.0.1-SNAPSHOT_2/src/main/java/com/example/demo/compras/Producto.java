package com.example.demo.compras;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;
    
    @Column(nullable = false)
    private Integer iva = 10;

    @Column(unique = true, nullable = false)
    private String codigoBarra; // NUEVO campo

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @DecimalMin(value = "0.0", inclusive = false, message = "El stock debe ser mayor a 0")
    private BigDecimal stockActual;

    private Boolean activo;
    
    
    // 
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal precioCosto; // Para usar en Órdenes de Compra
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
