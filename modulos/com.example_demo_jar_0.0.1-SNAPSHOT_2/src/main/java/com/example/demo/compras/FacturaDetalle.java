package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle")
@Data
public class FacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    
    // Campos fiscales necesarios para el Libro Compras
    private BigDecimal subtotalExenta;
    private BigDecimal subtotalIva5;
    private BigDecimal subtotalIva10;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    private FacturaCompra factura;
}