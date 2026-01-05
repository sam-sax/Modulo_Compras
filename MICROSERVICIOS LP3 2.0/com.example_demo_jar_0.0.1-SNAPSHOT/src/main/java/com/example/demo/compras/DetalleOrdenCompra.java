package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_orden_compra")
@Data
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "orden_id")
    private OrdenCompra ordenCompra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
