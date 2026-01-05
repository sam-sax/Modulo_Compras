/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.stock.dto;



import lombok.Data;
import java.math.BigDecimal;

@Data
public class MovimientoStockDTO {
    private Long productoId;
    private BigDecimal cantidad;
    private String tipo; // INGRESO o EGRESO
}
