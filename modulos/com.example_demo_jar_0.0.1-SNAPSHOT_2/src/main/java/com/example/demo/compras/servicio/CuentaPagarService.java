package com.example.demo.compras.servicio;

import com.example.demo.compras.CuentaPagar;
import com.example.demo.compras.dto.CuentaPagarDTO;
import java.util.List;

public interface CuentaPagarService {
    CuentaPagar registrarCuenta(CuentaPagarDTO dto);
    List<CuentaPagar> listarCuentas();
    // Agregamos el método para usar  SP de vencimientos
    List<CuentaPagar> obtenerVencidas();
}