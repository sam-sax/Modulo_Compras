package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import java.time.LocalDate;
import java.util.List;

public interface PersonaServicio {
    List<Persona> listar();
    
    // Firma actualizada para incluir Estado Civil en los filtros
    List<Persona> listarConFiltros(String q, String rol, Long ciudadId, String estadoCivil);
    
    Persona insertar(Persona persona);
    Persona actualizar(Persona persona);
    void eliminar(Long id);
    Persona buscarPorId(Long id);

    Persona buscarPorEmail(String email);
    Persona buscarPorTelefono(String telefono);
    Persona buscarPorNumeroCedula(String numeroCedula);
    Persona buscarPorRuc(String ruc);
    List<Persona> buscarPersonasSinUsuario();
    
    Long insertarPersonaSP(String nombres, String apellidos, String telefono, String direccion, LocalDate fechaNacimiento, Long usuarioId, Long usuarioAsociadoId, String email, String estadoCivil, String numeroCedula, String ruc, String razonSocial, String representanteLegal, String tipoPersona, Long ciudadId, Boolean esCliente, Boolean esProveedor, Boolean esVendedor, Boolean esComprador);
    void actualizarPersonaSP(Long id, String nombres, String apellidos, String telefono, String direccion, LocalDate fechaNacimiento, Long usuarioId, Long usuarioAsociadoId, String email, String estadoCivil, String numeroCedula, String ruc, String razonSocial, String representanteLegal, String tipoPersona, Long ciudadId, Boolean esCliente, Boolean esProveedor, Boolean esVendedor, Boolean esComprador);
    List<Persona> listarPersonaSP();
    Persona buscarPersonaSP(Long id);
}