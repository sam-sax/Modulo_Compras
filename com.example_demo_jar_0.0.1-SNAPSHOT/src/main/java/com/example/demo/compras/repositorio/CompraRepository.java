package com.example.demo.compras.repositorio;

import com.example.demo.compras.Compra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
