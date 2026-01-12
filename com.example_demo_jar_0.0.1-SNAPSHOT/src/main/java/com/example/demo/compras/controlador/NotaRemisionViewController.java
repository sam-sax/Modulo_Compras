package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.NotaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/remisiones")
public class NotaRemisionViewController {

    @Autowired private NotaRemisionService remisionService;

    @GetMapping("/vista")
    public String vistaRemisiones(Model model) {
        model.addAttribute("remisiones", remisionService.listarRemisiones());
        return "compras/remisiones";
    }
}
