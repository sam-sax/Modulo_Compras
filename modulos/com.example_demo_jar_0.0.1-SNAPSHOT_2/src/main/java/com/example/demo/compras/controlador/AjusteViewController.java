package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.AjusteStockDTO;
import com.example.demo.compras.servicio.AjusteStockService;
import com.example.demo.compras.servicio.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras/ajustes")
public class AjusteViewController {

    @Autowired private AjusteStockService ajusteService;
    @Autowired private ProductoService productoService;

    @GetMapping("/vista")
    public String vistaAjustes(Model model) {
        model.addAttribute("ajustes", ajusteService.listarAjustes());
        return "compras/ajustes";
    }

    @GetMapping("/nuevo")
    public String nuevoAjusteForm(Model model) {
        model.addAttribute("ajusteDTO", new AjusteStockDTO());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/ajustes-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AjusteStockDTO dto) {
        ajusteService.procesarAjuste(dto);
        return "redirect:/compras/ajustes/vista";
    }
}