package com.example.demo.compras.servicio;

import com.example.demo.compras.Ajuste;
import com.example.demo.compras.dto.AjusteDTO;
import java.util.List;

public interface AjusteService {
    Ajuste registrarAjuste(AjusteDTO dto);
    List<Ajuste> listarAjustes();
}