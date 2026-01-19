package com.example.demo.compras.repositorio;

import com.example.demo.compras.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
