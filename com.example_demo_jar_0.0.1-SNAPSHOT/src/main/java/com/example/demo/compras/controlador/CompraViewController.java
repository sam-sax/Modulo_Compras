package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/compras")
public class CompraViewController {

    @Autowired private CompraService compraService;

    @GetMapping("/vista")
    public String vistaCompras(Model model) {
        model.addAttribute("compras", compraService.listarCompras());
        return "compras/compras";
    }
}
