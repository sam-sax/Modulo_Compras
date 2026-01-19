package com.example.demo.compras.servicio;

import com.example.demo.compras.AjusteStock;
import com.example.demo.compras.dto.AjusteStockDTO;
import java.util.List;

public interface AjusteStockService {
    void procesarAjuste(AjusteStockDTO dto);
    List<AjusteStock> listarAjustes();
}