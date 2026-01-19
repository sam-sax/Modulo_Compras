package com.example.demo.compras.repositorio;

import com.example.demo.compras.LibroCompras;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroComprasRepository extends JpaRepository<LibroCompras, Long> {
    
    
}
