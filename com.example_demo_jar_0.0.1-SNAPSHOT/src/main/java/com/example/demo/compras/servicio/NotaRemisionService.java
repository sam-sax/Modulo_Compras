package com.example.demo.compras.servicio;

import com.example.demo.compras.NotaRemision;
import com.example.demo.compras.dto.NotaRemisionDTO;
import java.util.List;

public interface NotaRemisionService {
    NotaRemision registrarRemision(NotaRemisionDTO dto);
    List<NotaRemision> listarRemisiones();
}
