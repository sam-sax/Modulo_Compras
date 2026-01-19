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
@Table(name = "cuentas_pagar")
public class CuentaPagar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaVencimiento;
    private String estado; // PENDIENTE, PAGADA, PARCIAL

    @Column(precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal iva;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;
    
    @ManyToOne
    private Compra compra;


    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
}
