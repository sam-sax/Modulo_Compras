package com.example.demo.servicios;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.modelo.Cliente;
import com.example.demo.modelo.Persona;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.ClienteRepositorio;
import com.example.demo.repositorio.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteServicioImplement implements ClienteServicio {

    @Autowired private ClienteRepositorio clienteRepositorio;
    @Autowired private PersonaRepositorio personaRepositorio;
    @Autowired private PersonaServicio personaServicio;
    @Autowired private UsuarioServicio usuarioServicio; // Necesario para obtener usuario logueado

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    @Override
    public List<Persona> listarPersonasDisponibles() {
        return personaRepositorio.findAll().stream()
                .filter(p -> p.getEsCliente() == null || !p.getEsCliente())
                .collect(Collectors.toList());
    }

    @Override
    public Cliente guardarDesdeDTO(ClienteDTO dto) {
        Persona persona = personaRepositorio.findById(dto.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Marcar persona como cliente
        persona.setEsCliente(true);
        personaServicio.actualizar(persona);

        Cliente cliente = (dto.getIdCliente() != null)
                ? clienteRepositorio.findById(dto.getIdCliente()).orElse(new Cliente())
                : new Cliente();

        cliente.setPersona(persona);
        cliente.setLimiteCredito(dto.getLimiteCredito());
        cliente.setActivo(dto.isActivo());

        // === Asignar automáticamente el usuario logueado ===
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
    String username = auth.getName(); // el nombre del usuario logueado
    Optional<Usuario> usuarioLogueadoOpt = usuarioServicio.buscarUsuarioPorNombreSP(username);
    usuarioLogueadoOpt.ifPresent(cliente::setUsuario); // si existe, lo asigna al cliente
}


        return clienteRepositorio.save(cliente);
    }

    @Override
    public ClienteDTO obtenerDTOporId(Long id) {
        Cliente c = clienteRepositorio.findById(id).orElse(null);
        if (c == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(c.getId());
        dto.setIdPersona(c.getPersona().getIdPersona());
        dto.setLimiteCredito(c.getLimiteCredito());
        dto.setActivo(c.isActivo());
        return dto;
    }

    @Override
    public void eliminar(Long id) {
        Cliente c = clienteRepositorio.findById(id).orElse(null);
        if (c != null) {
            Persona p = c.getPersona();
            p.setEsCliente(false);
            personaServicio.actualizar(p);
        }
        clienteRepositorio.deleteById(id);
    }
}
