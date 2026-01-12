package com.example.demo.compras.controlador;

import com.example.demo.compras.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/productos")
public class ProductoViewController {

    @Autowired 
    private ProductoRepository productoRepo;

    @GetMapping("/vista")
    public String vistaProductos(Model model) {
        model.addAttribute("productos", productoRepo.findAll());
        return "compras/productos";
    }
}
