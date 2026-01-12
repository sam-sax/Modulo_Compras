package com.example.demo.compras.servicio;

import com.example.demo.compras.Pedido;
import com.example.demo.compras.dto.PedidoDTO;
import java.util.List;

public interface PedidoService {
    Pedido registrarPedido(PedidoDTO dto);
    List<Pedido> listarPedidos();
    Pedido aprobarPedido(Long pedidoId);
    Pedido anularPedido(Long pedidoId);
}
