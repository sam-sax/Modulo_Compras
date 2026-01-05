package com.example.demo.compras.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class OrdenCompraDTO {

    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private BigDecimal total;

    // Aquí va la lista de detalles correctos
    @NotEmpty(message = "Debe incluir al menos un detalle")
    private List<@Valid DetalleOrdenCompraDTO> detalles;

    // Getters y Setters
    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<DetalleOrdenCompraDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrdenCompraDTO> detalles) { this.detalles = detalles; }
}
