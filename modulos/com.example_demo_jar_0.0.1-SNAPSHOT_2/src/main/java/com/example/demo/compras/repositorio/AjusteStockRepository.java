package com.example.demo.compras.repositorio;

import com.example.demo.compras.AjusteStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AjusteStockRepository extends JpaRepository<AjusteStock, Long> {
}