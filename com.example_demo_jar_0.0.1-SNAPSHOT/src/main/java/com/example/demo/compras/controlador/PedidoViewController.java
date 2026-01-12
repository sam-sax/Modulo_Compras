package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/pedidos")
public class PedidoViewController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/vista")
    public String vistaPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarPedidos());
        return "compras/pedidos"; // templates/compras/pedidos.html
    }
}
