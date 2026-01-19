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
@Table(name = "notas_debito")
public class NotaDebito {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private BigDecimal montoSinIva;
    private BigDecimal iva;
    private BigDecimal montoTotal;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    // Relación Cabecera-Detalle
    @OneToMany(mappedBy = "notaDebito", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DetalleNotaDebito> detalles = new java.util.ArrayList<>();
}