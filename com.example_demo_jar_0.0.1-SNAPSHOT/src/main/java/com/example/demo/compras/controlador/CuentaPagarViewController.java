package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.CuentaPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/compras/cuentas")
public class CuentaPagarViewController {

    @Autowired private CuentaPagarService cuentaService;

    @GetMapping("/vista")
    public String vistaCuentas(Model model) {
        model.addAttribute("cuentas", cuentaService.listarCuentas());
        return "compras/cuentas";
    }
}
