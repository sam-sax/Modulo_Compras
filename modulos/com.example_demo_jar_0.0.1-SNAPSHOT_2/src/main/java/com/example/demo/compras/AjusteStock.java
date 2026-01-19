package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ajustes_stock")
@Data
public class AjusteStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private BigDecimal cantidad;

    // "ENTRADA" para sumar al stock, "SALIDA" para restar
    private String tipo; 

    private String motivo;

    private LocalDateTime fecha;
}