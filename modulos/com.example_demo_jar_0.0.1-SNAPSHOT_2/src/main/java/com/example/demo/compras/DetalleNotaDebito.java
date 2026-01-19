package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detalles_notas_debito")
public class DetalleNotaDebito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_debito_id")
    private NotaDebito notaDebito;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
}