package com.example.demo.servicios;

import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioServicio {

    // CRUD BASE
    List<Usuario> listar();
    Usuario insertar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    void eliminar(Long id);
    Usuario buscarPorId(Long id);
    boolean existeNombre(String usuario);

    // ==========================
    // SP con persona
    // ==========================
    Long insertarUsuarioSP(String usuario, String clave, Boolean estado, String rol, Persona persona);

    Long actualizarUsuarioSP(Long id, String usuario, String clave, Boolean estado, String rol, String usuarioModificador);

    Boolean eliminarUsuarioSP(Long id, String usuarioModificador);

    List<Usuario> listarUsuarioSP();

    Optional<Usuario> buscarUsuarioPorNombreSP(String usuario);

    boolean cambiarClaveSP(Long idUsuario, String nuevaClave, String usuarioModificador);
    
    
    // Nuevo método para cambiar solo rol
    Long cambiarRolUsuarioSP(Long idUsuario, String nuevoRol, Boolean estado, String usuarioModificador);
    
    
    void cambiarEstado(Long id, boolean activo);
    

}
