package com.example.demo.servicios;

import com.example.demo.modelo.Ciudad;
import com.example.demo.modelo.Pais;
import java.util.List;

public interface CiudadService {
    List<Ciudad> listarCiudades();
    List<Ciudad> listarCiudadesPorPais(Pais pais);
    Ciudad guardarCiudad(Ciudad ciudad);
    Ciudad actualizarCiudad(Long id, Ciudad ciudad);
    void eliminarCiudad(Long id);
    Ciudad obtenerPorId(Long id);
}