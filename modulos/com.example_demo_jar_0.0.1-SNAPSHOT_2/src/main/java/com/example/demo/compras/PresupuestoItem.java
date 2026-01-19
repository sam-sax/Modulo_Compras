package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "presupuestos_items")
public class PresupuestoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "presupuesto_id")
    private PresupuestoProveedor presupuesto;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    @Column(precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }
}