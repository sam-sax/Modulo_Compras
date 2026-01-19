package com.example.demo.compras.servicio;

import com.example.demo.compras.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> listarTodas();
    Categoria guardar(Categoria categoria);
    Categoria buscarPorId(Long id);
    void eliminar(Long id);
}