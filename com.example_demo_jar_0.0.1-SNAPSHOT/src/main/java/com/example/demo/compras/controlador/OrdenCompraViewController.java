package com.example.demo.compras.controlador;

import com.example.demo.compras.dto.MovimientoStockVistaDTO;
import com.example.demo.compras.dto.OrdenCompraVistaDTO;
import com.example.demo.compras.servicio.OrdenCompraService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/compras/ordenes")
public class OrdenCompraViewController {

    @Autowired
    private OrdenCompraService ordenService;

    @GetMapping("/vista")
    public String vistaOrdenes(
            @RequestParam(required = false) Long proveedorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        List<OrdenCompraVistaDTO> ordenes = ordenService.listarOrdenesFiltradas(desde, hasta, proveedorId);
        model.addAttribute("ordenes", ordenes);
        model.addAttribute("totalAcumulado", ordenService.totalAcumulado(desde, hasta, proveedorId));
        model.addAttribute("proveedorId", proveedorId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "compras/ordenes";
    }

    @GetMapping("/reporte")
    public String vistaReporte(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Long proveedorId,
            Model model) {

        model.addAttribute("ordenes", ordenService.listarOrdenesFiltradas(desde, hasta, proveedorId));
        model.addAttribute("totalAcumulado", ordenService.totalAcumulado(desde, hasta, proveedorId));
        model.addAttribute("proveedorId", proveedorId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "compras/reporteOrdenes";
    }

    @GetMapping("/{ordenId}/movimientos")
    public String vistaMovimientos(@PathVariable Long ordenId, Model model) {
        List<MovimientoStockVistaDTO> movimientos = ordenService.movimientosPorOrden(ordenId);
        model.addAttribute("movimientos", movimientos);
        model.addAttribute("ordenId", ordenId);
        return "compras/detalleMovimientos";
    }
}
