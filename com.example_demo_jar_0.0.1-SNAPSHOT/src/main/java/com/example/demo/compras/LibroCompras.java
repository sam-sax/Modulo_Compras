/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.compras;



import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "libro_compras")
public class LibroCompras {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String timbrado;
    private String numeroFactura;

    @Column(precision = 12, scale = 2)
    private BigDecimal baseImponible;

    @Column(precision = 12, scale = 2)
    private BigDecimal ivaCredito;

    @Column(precision = 12, scale = 2)
    private BigDecimal totalFactura;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;
}
