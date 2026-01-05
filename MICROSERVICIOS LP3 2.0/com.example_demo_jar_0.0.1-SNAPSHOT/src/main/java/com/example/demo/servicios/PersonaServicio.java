package com.example.demo.servicios;

import com.example.demo.modelo.Persona;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonaServicio {

    // CRUD clásicos
    List<Persona> listar();
    
    
    Persona insertar(Persona persona);
    Persona actualizar(Persona persona);
    void eliminar(Long id);
    Persona buscarPorId(Long id);
    Optional<Persona> getUsuarioByNombre(String persona);

    // SP específicos
    Long insertarPersonaSP(String nombres, String apellidos, String telefono,
                           String direccion, LocalDate fechaNacimiento, Long usuarioId,
                           Long usuarioAsociadoId, String email, String estadoCivil,
                           String ciudad, String pais, String numeroCedula, String ruc);

    Long actualizarPersonaSP(Long id, String nombres, String apellidos, String telefono,
                             String direccion, LocalDate fechaNacimiento, Long usuarioId,
                             Long usuarioAsociadoId, String email, String estadoCivil,
                             String ciudad, String pais, String numeroCedula, String ruc);

    Boolean eliminarPersonaSP(Long id, String usuarioEliminador);

    Persona buscarPersonaSP(Long id);
    List<Persona> listarPersonaSP();
    Optional<Persona> buscarPersonaPorNombreSP(String nombre);

    // Validaciones adicionales
    Persona buscarPorEmail(String email);
    Persona buscarPorTelefono(String telefono);
    List<Persona> buscarPersonasSinUsuario();
    Persona buscarPorNumeroCedula(String numeroCedula);
    Persona buscarPorRuc(String ruc);
}
