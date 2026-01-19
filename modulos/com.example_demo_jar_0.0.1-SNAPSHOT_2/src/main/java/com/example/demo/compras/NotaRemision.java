package com.example.demo.compras;

import com.example.demo.modelo.Vendedor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "notas_remision")
public class NotaRemision {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Vendedor vendedor;

    // Relación para los productos que vienen en el camión
    @OneToMany(mappedBy = "notaRemision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleNotaRemision> detalles = new ArrayList<>();
}