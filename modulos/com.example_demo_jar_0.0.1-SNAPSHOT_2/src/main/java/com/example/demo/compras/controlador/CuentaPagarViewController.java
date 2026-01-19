package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.CuentaPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/compras/cuentas")
public class CuentaPagarViewController {

    @Autowired private CuentaPagarService cuentaService;

    @GetMapping("/vista")
    public String vistaCuentas(@RequestParam(name = "vencidas", required = false) Boolean vencidas, Model model) {
        if (Boolean.TRUE.equals(vencidas)) {
            model.addAttribute("cuentas", cuentaService.obtenerVencidas());
            model.addAttribute("titulo", "Cuentas Vencidas a la Fecha");
        } else {
            model.addAttribute("cuentas", cuentaService.listarCuentas());
            model.addAttribute("titulo", "Listado General de Cuentas a Pagar");
        }
        return "compras/cuentas";
    }
}