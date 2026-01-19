package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "facturas_compra")
public class FacturaCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroFactura; // Ej: 001-001-0001234

    private LocalDate fechaEmision;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    private Double totalExenta = 0.0;
    private Double totalIva5 = 0.0;
    private Double totalIva10 = 0.0;
    private Double totalFinal;

    @Enumerated(EnumType.STRING)
    private CondicionPago condicion; // CONTADO o CREDITO

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<FacturaDetalle> detalles;
}