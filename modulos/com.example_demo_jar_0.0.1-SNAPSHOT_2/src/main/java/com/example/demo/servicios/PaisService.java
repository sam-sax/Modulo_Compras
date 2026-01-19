package com.example.demo.servicios;



import com.example.demo.modelo.Pais;
import java.util.List;

public interface PaisService {
    List<Pais> listarPaises();
    Pais guardarPais(Pais pais);
    Pais actualizarPais(Long id, Pais pais);
    void eliminarPais(Long id);
    Pais obtenerPorId(Long id);
}
