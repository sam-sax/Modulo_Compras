package com.example.demo.compras.servicio;

import com.example.demo.compras.CuentaPagar;
import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.CuentaPagarDTO;
import com.example.demo.compras.repositorio.CuentaPagarRepository;
import com.example.demo.compras.repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuentaPagarServiceImpl implements CuentaPagarService {

    @Autowired private CuentaPagarRepository cuentaRepo;
    @Autowired private ProveedorRepository proveedorRepo;

    @Override
    public CuentaPagar registrarCuenta(CuentaPagarDTO dto) {
        Proveedor proveedor = proveedorRepo.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        CuentaPagar cuenta = new CuentaPagar();
        cuenta.setProveedor(proveedor);
        cuenta.setFechaVencimiento(dto.getFechaVencimiento());
        cuenta.setEstado(dto.getEstado());
        cuenta.setSubtotal(dto.getSubtotal());
        cuenta.setIva(dto.getIva());
        cuenta.setTotal(dto.getTotal());

        return cuentaRepo.save(cuenta);
    }

    @Override
    public List<CuentaPagar> listarCuentas() {
        return cuentaRepo.findAll();
    }
}
