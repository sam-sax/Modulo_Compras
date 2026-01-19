/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "presupuestos_proveedor")
public class PresupuestoProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private String numero;

    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal iva;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;
    
    
    private LocalDate validezHasta; // Para la validación de expiración

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresupuestoItem> items = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
private EstadoPresupuesto estado = EstadoPresupuesto.PENDIENTE;

public enum EstadoPresupuesto {
    PENDIENTE, ACEPTADO, RECHAZADO, CONVERTIDO
}
}
