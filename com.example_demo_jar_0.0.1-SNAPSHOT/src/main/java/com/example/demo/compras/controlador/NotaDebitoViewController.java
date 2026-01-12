package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.NotaDebitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/notas-debito")
public class NotaDebitoViewController {

    @Autowired private NotaDebitoService notaService;

    @GetMapping("/vista")
    public String vistaNotas(Model model) {
        model.addAttribute("notasDebito", notaService.listarNotas());
        return "compras/notasDebito";
    }
}
