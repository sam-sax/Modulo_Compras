package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "compras")
public class Compra {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    
    // AGREGAR ESTO:
    @Column(name = "numero_factura", length = 20)
    private String numeroFactura;

    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal iva;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "orden_compra_id")
    private OrdenCompra ordenCompra;
    
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;
    
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
}