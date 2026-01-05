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

    // ============================
    // 1️⃣ Vista principal (listado)
    // ============================
    @GetMapping("/vista")
    public String vistaOrdenes(
            @RequestParam(required = false) Long proveedorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            Model model) {

        List<OrdenCompraVistaDTO> ordenes = ordenService.listarOrdenesFiltradas(desde, hasta, proveedorId);
        model.addAttribute("ordenes", ordenes);

        BigDecimal totalAcumulado = ordenService.totalAcumulado(desde, hasta, proveedorId);
        model.addAttribute("totalAcumulado", totalAcumulado);

        model.addAttribute("proveedorId", proveedorId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "compras/ordenes"; // templates/compras/ordenes.html
    }

    // ============================
    // 2️⃣ Reporte filtrable
    // ============================
    @GetMapping("/reporte")
    public String vistaReporte(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Long proveedorId,
            Model model) {

        List<OrdenCompraVistaDTO> ordenes = ordenService.listarOrdenesFiltradas(desde, hasta, proveedorId);
        BigDecimal total = ordenService.totalAcumulado(desde, hasta, proveedorId);

        model.addAttribute("ordenes", ordenes);
        model.addAttribute("totalAcumulado", total);
        model.addAttribute("proveedorId", proveedorId);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "compras/reporteOrdenes"; // templates/compras/reporteOrdenes.html
    }

                // ============================
            // 3️⃣ Movimientos de stock (vista HTML)
            // ============================
       @GetMapping("/{ordenId}/movimientos")
public String vistaMovimientos(@PathVariable Long ordenId, Model model) {
    List<MovimientoStockVistaDTO> movimientos = ordenService.movimientosPorOrden(ordenId);
    model.addAttribute("movimientos", movimientos);
    model.addAttribute("ordenId", ordenId);

    return "compras/detalleMovimientos"; // debe existir en src/main/resources/templates/compras/
}


}
