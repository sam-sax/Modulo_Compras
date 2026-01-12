package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.AjusteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/ajustes")
public class AjusteViewController {

    @Autowired private AjusteService ajusteService;

    @GetMapping("/vista")
    public String vistaAjustes(Model model) {
        model.addAttribute("ajustes", ajusteService.listarAjustes());
        return "compras/ajustes";
    }
}
