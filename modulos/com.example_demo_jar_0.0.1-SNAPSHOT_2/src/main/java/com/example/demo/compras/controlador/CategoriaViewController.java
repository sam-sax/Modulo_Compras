package com.example.demo.compras.controlador;

import com.example.demo.compras.Categoria;
import com.example.demo.compras.servicio.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras/categorias")
public class CategoriaViewController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/vista")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("nuevaCategoria", new Categoria());
        return "compras/categorias";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        categoriaService.guardar(categoria);
        return "redirect:/compras/categorias/vista";
    }
}