package com.example.demo.servicios;

import com.example.demo.modelo.Comprador;
import java.util.List;

public interface CompradorService {
    
    // Cambiamos el parámetro para que reciba la ENTIDAD 'Comprador'
    // Esto es para que coincida con tu ControladorAdmin y la lógica de Vendedor
    Comprador registrar(Comprador comprador); 

    List<Comprador> listarActivos();

    Comprador buscarPorUsuario(String usuario);
}