package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.PedidoDTO;
import com.example.demo.compras.servicio.PedidoService;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras/pedidos")
public class PedidoViewController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/vista")
    public String vistaPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "compras/pedidos";
    }

    @GetMapping("/nuevo")
    public String nuevoPedidoForm(Model model) {
        model.addAttribute("pedidoDTO", new PedidoDTO());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/pedidos-form";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute PedidoDTO pedidoDTO, RedirectAttributes ra) {
        try {
            pedidoService.registrarPedido(pedidoDTO);
            ra.addFlashAttribute("mensaje", "Pedido registrado exitosamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al registrar pedido: " + e.getMessage());
        }
        return "redirect:/compras/pedidos/vista";
    }

    @PostMapping("/aprobar/{id}")
    public String aprobarPedido(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pedidoService.aprobarPedido(id);
            ra.addFlashAttribute("mensaje", "Pedido aprobado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/compras/pedidos/vista";
    }

    @PostMapping("/anular/{id}")
    public String anularPedido(@PathVariable Long id, RedirectAttributes ra) {
        try {
            pedidoService.anularPedido(id);
            ra.addFlashAttribute("mensaje", "Pedido anulado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/compras/pedidos/vista";
    }
}