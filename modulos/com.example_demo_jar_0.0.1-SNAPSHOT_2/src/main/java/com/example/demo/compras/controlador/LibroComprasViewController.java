package com.example.demo.compras.controlador;

import com.example.demo.compras.servicio.LibroComprasService;
import com.example.demo.compras.servicio.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/compras/libro")
public class LibroComprasViewController {

    @Autowired private LibroComprasService libroService;
    @Autowired private ProveedorService proveedorService;

    @GetMapping("/vista")
    public String vistaLibros(
            @RequestParam(name = "mes", required = false) Integer mes,
            @RequestParam(name = "anio", required = false) Integer anio,
            @RequestParam(name = "proveedorId", required = false) Long proveedorId,
            Model model) {

        // Carga de selectores
        model.addAttribute("proveedores", proveedorService.listarProveedores());

        // Lógica de filtrado por SP o Lista General
        if (proveedorId != null) {
            model.addAttribute("libros", libroService.listarPorProveedor(proveedorId));
        } else {
            model.addAttribute("libros", libroService.listarLibros());
        }

        // Lógica para el Reporte de IVA Mensual (SP)
        if (mes != null && anio != null) {
            BigDecimal ivaMensual = libroService.obtenerIvaMensual(mes, anio);
            model.addAttribute("ivaMensual", ivaMensual);
            model.addAttribute("mesFiltro", mes);
            model.addAttribute("anioFiltro", anio);
        }

        return "compras/libros";
    }
}