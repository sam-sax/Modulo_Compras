package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import com.example.demo.compras.PedidoItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado; // BORRADOR, APROBADO, ANULADO

    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal iva = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    private Long compradorId;
    private Long proveedorId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> items = new ArrayList<>();

    // Método para calcular totales
    public void calcularTotales(double porcentajeIVA) {
        subtotal = items.stream()
                .map(PedidoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        iva = subtotal.multiply(BigDecimal.valueOf(porcentajeIVA)).setScale(2, BigDecimal.ROUND_HALF_UP);
        total = subtotal.add(iva);
    }

    public enum EstadoPedido {
        BORRADOR, APROBADO, ANULADO
    }
}
