package com.example.demo.compras;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
@Data
public class OrdenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
 
    
    
    // proceso estado orden 
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
        private EstadoOrden estado = EstadoOrden.CREADA;

    @Column(precision = 12, scale = 2)
        private BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
        private BigDecimal iva;

    @Column(name = "total_final", precision = 12, scale = 2)
        private BigDecimal totalFinal;



    
    
    
    //Relaciones
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
        private Proveedor proveedor;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<DetalleOrdenCompra> detalles;






}
