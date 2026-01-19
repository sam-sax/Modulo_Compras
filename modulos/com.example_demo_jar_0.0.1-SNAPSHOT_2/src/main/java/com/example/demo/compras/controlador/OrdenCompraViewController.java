package com.example.demo.compras.controlador;

import com.example.demo.compras.Pedido;
import com.example.demo.compras.dto.DetalleOrdenCompraDTO;
import com.example.demo.compras.dto.MovimientoStockVistaDTO;
import com.example.demo.compras.dto.OrdenCompraDTO;
import com.example.demo.compras.dto.OrdenCompraVistaDTO;
import com.example.demo.compras.servicio.OrdenCompraService;
import com.example.demo.compras.servicio.PedidoService;
import com.example.demo.compras.servicio.ProductoService;
import com.example.demo.compras.servicio.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/compras/ordenes")
public class OrdenCompraViewController {

    @Autowired private OrdenCompraService ordenService;
    @Autowired private PedidoService pedidoService;   
    @Autowired private ProductoService productoService; 
    @Autowired private ProveedorService proveedorService;

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

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        OrdenCompraDTO dto = new OrdenCompraDTO();
        dto.setFecha(LocalDate.now());
        
        model.addAttribute("ordenCompraDTO", dto);
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        return "compras/ordenes-form";
    }

    @PostMapping("/guardar")
    public String guardarOrden(@ModelAttribute OrdenCompraDTO dto, RedirectAttributes ra) {
        try {
            ordenService.registrarOrden(dto);
            ra.addFlashAttribute("mensaje", "Orden de Compra generada con éxito");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error al generar orden: " + e.getMessage());
        }
        return "redirect:/compras/ordenes/vista";
    }

    @GetMapping("/confirmar")
    public String confirmarOrden(@RequestParam Long id, RedirectAttributes ra) {
        try {
            ordenService.confirmarOrden(id);
            ra.addFlashAttribute("mensaje", "Stock actualizado y orden confirmada");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/compras/ordenes/vista";
    }

    @GetMapping("/anular")
    public String anularOrden(@RequestParam Long id, RedirectAttributes ra) {
        try {
            ordenService.anularOrden(id);
            ra.addFlashAttribute("mensaje", "Orden anulada correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/compras/ordenes/vista";
    }

    @GetMapping("/reporte")
    public String vistaReporte(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) Long proveedorId,
            Model model) {

        model.addAttribute("ordenes", ordenService.listarOrdenesFiltradas(desde, hasta, proveedorId));
        model.addAttribute("totalAcumulado", ordenService.totalAcumulado(desde, hasta, proveedorId));
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);

        return "compras/reporteOrdenes";
    }

    // CAMBIO AQUÍ: Añadimos "/ver/" para que la URL sea única
    @GetMapping("/ver/{ordenId}/movimientos")
    public String vistaMovimientos(@PathVariable Long ordenId, Model model) {
        List<MovimientoStockVistaDTO> movimientos = ordenService.movimientosPorOrden(ordenId);
        model.addAttribute("movimientos", movimientos);
        model.addAttribute("ordenId", ordenId);
        return "compras/detalleMovimientos";
    }
    
    @GetMapping("/nuevo-desde-pedido/{pedidoId}")
    public String nuevaOrdenDesdePedido(@PathVariable Long pedidoId, Model model) {
        Pedido pedido = pedidoService.buscarPorId(pedidoId);
        
        OrdenCompraDTO ordenDTO = new OrdenCompraDTO();
        ordenDTO.setFecha(LocalDate.now());
        
        List<DetalleOrdenCompraDTO> detalles = pedido.getItems().stream().map(item -> {
            DetalleOrdenCompraDTO d = new DetalleOrdenCompraDTO();
            d.setProductoId(item.getProducto().getId());
            d.setCantidad(new BigDecimal(item.getCantidad()));
            d.setPrecioUnitario(item.getProducto().getPrecio()); 
            return d;
        }).toList();
        
        ordenDTO.setDetalles(detalles);

        model.addAttribute("ordenCompraDTO", ordenDTO);
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("productos", productoService.listarProductos());
        
        return "compras/ordenes-form";
    }
}