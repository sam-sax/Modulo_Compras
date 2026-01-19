package com.example.demo.compras.repositorio;

import com.example.demo.compras.PedidoItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    // Ejemplo: obtener todos los items de un pedido
    List<PedidoItem> findByPedidoId(Long pedidoId);
}
