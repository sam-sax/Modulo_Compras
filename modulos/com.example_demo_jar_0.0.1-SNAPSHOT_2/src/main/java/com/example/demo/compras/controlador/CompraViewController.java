package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.CompraDTO;
import com.example.demo.compras.servicio.CompraService;
import com.example.demo.compras.servicio.ProveedorService;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras/compras")
public class CompraViewController {

    @Autowired private CompraService compraService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private ProductoService productoService;

    // Listado principal
    @GetMapping("/vista")
    public String vistaCompras(Model model) {
        model.addAttribute("compras", compraService.listarCompras());
        return "compras/compras"; // compras.html
    }

    // Formulario de nueva compra
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("compraDTO", new CompraDTO());
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/compras-form"; // compras-form.html
    }

    // Guardar la compra
    @PostMapping("/guardar")
    public String guardarCompra(@ModelAttribute("compraDTO") CompraDTO compraDTO) {
        compraService.registrarCompra(compraDTO);
        return "redirect:/compras/compras/vista";
    }
}