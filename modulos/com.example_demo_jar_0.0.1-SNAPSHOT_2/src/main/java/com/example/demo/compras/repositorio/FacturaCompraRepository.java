/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.compras.repositorio;

import com.example.demo.compras.FacturaCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sxsax
 */
@Repository
public interface FacturaCompraRepository extends JpaRepository<FacturaCompra, Long> {
    // Permite buscar facturas por número de comprobante o proveedor
}