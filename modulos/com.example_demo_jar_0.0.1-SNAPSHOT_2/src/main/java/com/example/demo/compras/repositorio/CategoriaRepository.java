package com.example.demo.compras.repositorio;

import com.example.demo.compras.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}