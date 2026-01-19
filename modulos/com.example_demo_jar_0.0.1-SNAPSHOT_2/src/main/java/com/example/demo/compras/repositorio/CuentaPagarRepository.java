package com.example.demo.compras.repositorio;

import com.example.demo.compras.CuentaPagar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaPagarRepository extends JpaRepository<CuentaPagar, Long> {
}
