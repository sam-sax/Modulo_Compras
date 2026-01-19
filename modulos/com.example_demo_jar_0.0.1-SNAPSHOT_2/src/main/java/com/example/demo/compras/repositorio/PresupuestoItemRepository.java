package com.example.demo.compras.repositorio;

import com.example.demo.compras.PresupuestoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestoItemRepository extends JpaRepository<PresupuestoItem, Long> {
}