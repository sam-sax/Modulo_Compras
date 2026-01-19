package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalles_notas_credito")
public class DetalleNotaCredito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_credito_id")
    private NotaCredito notaCredito;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto; // Usando tu entidad Productos [cite: 98]

    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
}