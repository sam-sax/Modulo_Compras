package com.example.demo.servicios;

import com.example.demo.dto.UsuarioDetalleDTO;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServicioImpl implements UsuarioServicio, UserDetailsService {

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = buscarUsuarioPorNombreSP(username);
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().isEstado()) {
            throw new UsernameNotFoundException("Usuario no encontrado o inactivo: " + username);
        }
        Usuario usuario = usuarioOpt.get();
        String rol = "ROLE_" + usuario.getRol().toUpperCase();
        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getClave())
                .authorities(Collections.singletonList(() -> rol))
                .build();
    }

    @Override
    public List<UsuarioDetalleDTO> listarUsuariosDetalleSP() {
        List<Usuario> usuarios = listarUsuarioSP();
        return usuarios.stream().map(u -> {
            UsuarioDetalleDTO dto = new UsuarioDetalleDTO();
            dto.setId(u.getId());
            dto.setUsuario(u.getUsuario());
            dto.setRol(u.getRol());
            dto.setEstado(u.isEstado());
            if (u.getPersona() != null) {
                dto.setNombreCompletoPersona(u.getPersona().getNombres() + " " + u.getPersona().getApellidos());
            }
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listar() { return usuarioRepositorio.findAll(); }

    @Override
    public Usuario insertar(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        if (usuario.getId() == null) throw new RuntimeException("ID requerido");
        if (usuario.getClave() != null && !usuario.getClave().isEmpty()) {
            usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        }
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public boolean existeNombre(String usuario) {
        return usuarioRepositorio.existsByUsuario(usuario);
    }

    @Override
    @Transactional
    public Long insertarUsuarioSP(String usuario, String clave, Boolean estado, String rol, Persona persona) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_insertarUsuario");
        query.registerStoredProcedureParameter("p_usuario", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_clave", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_estado", Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rol", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id_persona", Integer.class, ParameterMode.IN);

        query.setParameter("p_usuario", usuario);
        query.setParameter("p_clave", passwordEncoder.encode(clave));
        query.setParameter("p_estado", estado != null ? estado : true);
        query.setParameter("p_rol", rol != null ? rol : "USER");
        query.setParameter("p_id_persona", persona.getIdPersona().intValue());

        query.execute();
        Object result = query.getSingleResult();
        return ((Number) result).longValue();
    }

    @Override
    @Transactional
    public Long actualizarUsuarioSP(Long id, String usuario, String clave, Boolean estado, String rol, String usuarioModificador) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_actualizarUsuario");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_clave", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_estado", Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rol", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario_modificador", String.class, ParameterMode.IN);

        query.setParameter("p_id", id.intValue());
        query.setParameter("p_usuario", usuario);
        query.setParameter("p_clave", passwordEncoder.encode(clave));
        query.setParameter("p_estado", estado);
        query.setParameter("p_rol", rol);
        query.setParameter("p_usuario_modificador", usuarioModificador);

        query.execute();
        return id;
    }

    @Override
    @Transactional
    public Boolean eliminarUsuarioSP(Long id, String usuarioModificador) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_eliminarUsuario");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario_modificador", String.class, ParameterMode.IN);
        query.setParameter("p_id", id.intValue());
        query.setParameter("p_usuario_modificador", usuarioModificador);
        query.execute();
        return ((Number) query.getSingleResult()).intValue() > 0;
    }

    @Override
    public List<Usuario> listarUsuarioSP() {
        return entityManager.createStoredProcedureQuery("sp_listarUsuario", Usuario.class).getResultList();
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorNombreSP(String usuario) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_buscarUsuarioPorNombre", Usuario.class);
        query.registerStoredProcedureParameter("p_usuario", String.class, ParameterMode.IN);
        query.setParameter("p_usuario", usuario);
        List<Usuario> lista = query.getResultList();
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    @Override
    @Transactional
    public boolean cambiarClaveSP(Long idUsuario, String nuevaClave, String usuarioModificador) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_cambiarClaveUsuario");
        query.registerStoredProcedureParameter("p_id_usuario", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_nueva_clave", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario_modificador", String.class, ParameterMode.IN);
        query.setParameter("p_id_usuario", idUsuario.intValue());
        query.setParameter("p_nueva_clave", passwordEncoder.encode(nuevaClave));
        query.setParameter("p_usuario_modificador", usuarioModificador);
        query.execute();
        return ((Number) query.getSingleResult()).intValue() > 0;
    }

    @Override
    @Transactional
    public Long cambiarRolUsuarioSP(Long idUsuario, String nuevoRol, Boolean estado, String usuarioModificador) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_cambiarRolUsuario");
        query.registerStoredProcedureParameter("p_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rol", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_estado", Boolean.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_usuario_modificador", String.class, ParameterMode.IN);
        query.setParameter("p_id", idUsuario.intValue());
        query.setParameter("p_rol", nuevoRol);
        query.setParameter("p_estado", estado);
        query.setParameter("p_usuario_modificador", usuarioModificador);
        query.execute();
        return idUsuario;
    }

    @Override
    public void cambiarEstado(Long id, boolean activo) {
        Usuario u = usuarioRepositorio.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setEstado(activo);
        usuarioRepositorio.save(u);
    }
}