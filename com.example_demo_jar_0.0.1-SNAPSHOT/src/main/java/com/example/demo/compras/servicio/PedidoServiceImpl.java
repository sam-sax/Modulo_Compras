package com.example.demo.compras.servicio;


import com.example.demo.compras.Pedido;
import com.example.demo.compras.Pedido.EstadoPedido;
import com.example.demo.compras.dto.PedidoDTO;
import com.example.demo.compras.repositorio.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired private PedidoRepository pedidoRepo;

    @Override
    public Pedido registrarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFecha(dto.getFecha());
        pedido.setEstado(EstadoPedido.BORRADOR); // Enum, no String
        return pedidoRepo.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepo.findAll();
    }

    @Override
    public Pedido aprobarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(EstadoPedido.APROBADO);
        return pedidoRepo.save(pedido);
    }

    @Override
    public Pedido anularPedido(Long pedidoId) {
        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(EstadoPedido.ANULADO);
        return pedidoRepo.save(pedido);
    }
}
