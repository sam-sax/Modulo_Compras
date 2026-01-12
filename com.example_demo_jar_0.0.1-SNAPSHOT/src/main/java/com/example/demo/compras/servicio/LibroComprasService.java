package com.example.demo.compras.servicio;

import com.example.demo.compras.LibroCompras;
import com.example.demo.compras.dto.LibroComprasDTO;
import java.util.List;

public interface LibroComprasService {
    LibroCompras registrarLibro(LibroComprasDTO dto);
    List<LibroCompras> listarLibros();
}
