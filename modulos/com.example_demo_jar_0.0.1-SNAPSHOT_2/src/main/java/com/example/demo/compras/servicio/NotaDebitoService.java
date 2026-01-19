package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaDebito;
import com.example.demo.compras.dto.NotaDebitoDTO;
import java.util.List;

public interface NotaDebitoService {
    NotaDebito registrarNota(NotaDebitoDTO dto);
    List<NotaDebito> listarNotas();
    // Tu SP
    List<NotaDebito> listarPorProveedor(Long proveedorId);
}