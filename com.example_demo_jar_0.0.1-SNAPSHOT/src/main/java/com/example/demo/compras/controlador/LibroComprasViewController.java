package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.LibroComprasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/libro")
public class LibroComprasViewController {

    @Autowired private LibroComprasService libroService;

    @GetMapping("/vista")
    public String vistaLibros(Model model) {
        model.addAttribute("libros", libroService.listarLibros());
        return "compras/libros";
    }
}
