package com.example.demo.compras;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

@Entity
@Table(name = "detalle_compra")
@Data
public class DetalleCompra {

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
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // --- LÓGICA DE CÁLCULO AUTOMÁTICO ---

    @PrePersist
    @PreUpdate
    public void calcularSubtotal() {
        if (this.cantidad != null && this.precioUnitario != null) {
            // subtotal = cantidad * precioUnitario
            this.subtotal = this.precioUnitario.multiply(this.cantidad);
        } else {
            this.subtotal = BigDecimal.ZERO;
        }
    }
}