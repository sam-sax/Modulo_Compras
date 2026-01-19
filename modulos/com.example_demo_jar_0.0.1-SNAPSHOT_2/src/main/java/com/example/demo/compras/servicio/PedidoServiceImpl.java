package com.example.demo.compras.servicio;


import com.example.demo.compras.Pedido;
import com.example.demo.compras.Pedido.EstadoPedido;
import com.example.demo.compras.PedidoItem;
import com.example.demo.compras.Producto;
import com.example.demo.compras.dto.PedidoDTO;
import com.example.demo.compras.dto.PedidoItemDTO;
import com.example.demo.compras.repositorio.PedidoItemRepository;
import com.example.demo.compras.repositorio.PedidoRepository;
import com.example.demo.compras.repositorio.ProductoRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired private PedidoRepository pedidoRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private PedidoItemRepository itemRepo;

    @Override
    @Transactional
    public Pedido registrarPedido(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFecha(dto.getFecha());
        pedido.setEstado(EstadoPedido.BORRADOR);
        pedido.setItems(new ArrayList<>());

        // Guardamos primero para tener el ID
        Pedido pedidoGuardado = pedidoRepo.save(pedido);

        if (dto.getItems() != null) {
            for (PedidoItemDTO itemDto : dto.getItems()) {
                Producto producto = productoRepo.findById(itemDto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                PedidoItem item = new PedidoItem();
                item.setPedido(pedidoGuardado);
                item.setProducto(producto);
                item.setCantidad(itemDto.getCantidad());
                
                itemRepo.save(item);
                pedidoGuardado.getItems().add(item);
            }
        }
        return pedidoGuardado;
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
    
    @Override
public Pedido buscarPorId(Long id) {
    return pedidoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
}
}
