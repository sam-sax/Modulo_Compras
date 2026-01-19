package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/compras/cierre")
public class CompraCierreViewController {

    @Autowired 
    private CompraService compraService;

    @GetMapping("/vista")
    public String verPantallaCierre() {
        return "compras/cierre-compras"; // Nombre del archivo HTML
    }

    @PostMapping("/procesar")
    public String procesarCierre(@RequestParam("fecha") LocalDate fecha, RedirectAttributes ra) {
        try {
            compraService.ejecutarCierre(fecha);
            ra.addFlashAttribute("mensaje", "Proceso de cierre ejecutado con éxito para: " + fecha);
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al ejecutar el cierre: " + e.getMessage());
        }
        return "redirect:/compras/cierre/vista";
    }
}