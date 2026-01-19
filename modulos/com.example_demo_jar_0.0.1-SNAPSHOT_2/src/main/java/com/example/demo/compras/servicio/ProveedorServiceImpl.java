package com.example.demo.compras.servicio;

import com.example.demo.compras.Proveedor;
import com.example.demo.compras.dto.ProveedorDTO;
import com.example.demo.compras.repositorio.ProveedorRepository;
import com.example.demo.modelo.Persona;
import com.example.demo.repositorio.PersonaRepositorio; // Usando tu repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired 
    private ProveedorRepository proveedorRepo;

    @Autowired 
    private PersonaRepositorio personaRepo; // Inyectamos tu repositorio de personas

    @Override
    @Transactional
    public Proveedor registrarProveedor(ProveedorDTO dto) {
        // 1. Buscamos la persona en tu tabla central 'personas'
        Persona persona = personaRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("La persona con ID " + dto.getId()+ " no existe"));

        // 2. Creamos la entidad Proveedor con sus "datos propios" (copiados)
        Proveedor proveedor = new Proveedor();
        
        // Vinculamos la relación OneToOne
        proveedor.setPersona(persona);
        
        // Copiamos los datos para que queden fijos en el módulo de compras
        // Usamos los métodos de tu entidad Persona
        proveedor.setRazonSocial(dto.getRazonSocial() != null ? dto.getRazonSocial() : persona.getNombreMostrable());
        proveedor.setRuc(dto.getRuc() != null ? dto.getRuc() : persona.getRuc());
        proveedor.setDireccion(dto.getDireccion() != null ? dto.getDireccion() : persona.getDireccion());
        proveedor.setTelefono(dto.getTelefono() != null ? dto.getTelefono() : persona.getTelefono());
        proveedor.setEmail(dto.getEmail() != null ? dto.getEmail() : persona.getEmail());
        
        // Estado por defecto
        proveedor.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        
        // 3. ACTUALIZACIÓN IMPORTANTE: Marcamos a la persona como proveedor
        persona.setEsProveedor(true);
        personaRepo.save(persona); 
        
        return proveedorRepo.save(proveedor);
    }

    @Override
    public List<Proveedor> listarProveedores() {
        // Retorna todos los registros de la tabla proveedores
        return proveedorRepo.findAll();
    }
}