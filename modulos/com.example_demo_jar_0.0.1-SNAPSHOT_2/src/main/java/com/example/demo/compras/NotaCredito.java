/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.compras;



import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "notas_credito")
public class NotaCredito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private BigDecimal montoSinIva;
    private BigDecimal iva;
    private BigDecimal montoTotal;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    // AGREGAR ESTO: Relación Cabecera-Detalle [cite: 66]
    @OneToMany(mappedBy = "notaCredito", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DetalleNotaCredito> detalles = new java.util.ArrayList<>();
}