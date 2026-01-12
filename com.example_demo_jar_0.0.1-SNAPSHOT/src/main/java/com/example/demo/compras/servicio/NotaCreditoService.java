package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaCredito;
import com.example.demo.compras.dto.NotaCreditoDTO;
import java.util.List;

public interface NotaCreditoService {
    NotaCredito registrarNota(NotaCreditoDTO dto);
    List<NotaCredito> listarNotas();
}
