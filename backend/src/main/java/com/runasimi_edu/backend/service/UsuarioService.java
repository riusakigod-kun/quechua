package com.runasimi_edu.backend.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.runasimi_edu.backend.dto.request.UsuarioRequest;
import com.runasimi_edu.backend.dto.response.UsuarioResponse;
import com.runasimi_edu.backend.model.Grado;
import com.runasimi_edu.backend.model.Usuario;
import com.runasimi_edu.backend.model.Usuario.RolUsuario;
import com.runasimi_edu.backend.repository.GradoRepository;
import com.runasimi_edu.backend.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final GradoRepository gradoRepository;

    // Buscar por ID
    public Optional<UsuarioResponse> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::convertirAResponse);
    }

    // Buscar por DNI
    public Optional<UsuarioResponse> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni).map(this::convertirAResponse);
    }

    // Buscar por email
    public Optional<UsuarioResponse> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).map(this::convertirAResponse);
    }

    // Verificar existencia por DNI
    public boolean existePorDni(String dni) {
        return usuarioRepository.existsByDni(dni);
    }

    // Verificar existencia por email
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Listar usuarios por rol
    public List<UsuarioResponse> listarPorRol(RolUsuario rol) {
    return usuarioRepository.findByRol(rol).stream()
            .map(this::convertirAResponse)
            .collect(Collectors.toList());
}


    // Listar usuarios por grado
    public List<UsuarioResponse> listarPorGrado(Long gradoId) {
    return usuarioRepository.findByGradoId(gradoId).stream()
            .map(this::convertirAResponse)
            .collect(Collectors.toList());
}


    // Listar usuarios por estado activo/inactivo
    public List<Usuario> listarPorActivo(Boolean estado) {
        return usuarioRepository.findByActivo(estado);
    }

    // Listar por rol y estado
   public List<UsuarioResponse> listarPorRolYActivo(RolUsuario rol, boolean estado) {
    return usuarioRepository.findByRolAndActivo(rol, estado).stream()
            .map(this::convertirAResponse)
            .collect(Collectors.toList());
}

    // Guardar usuario directamente (interno)
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    // Listar usuarios por rol y grado
    public List<UsuarioResponse> listarPorRolYGrado(RolUsuario rol, Long gradoId) {
    return usuarioRepository.findByRolAndGradoId(rol, gradoId).stream()
            .map(this::convertirAResponse)
            .collect(Collectors.toList());
}

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Listar todos los usuarios (convertidos a DTO)
    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream().map(this::convertirAResponse).collect(Collectors.toList());
    }

    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Validar grado según el rol
    RolUsuario rol = RolUsuario.valueOf(request.getRol().toUpperCase());
    Grado grado = null;
    
    if (request.getGradoId() != null) {
        grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("El grado especificado no existe."));
    } else if (rol == RolUsuario.ALUMNO) {
        throw new RuntimeException("El campo gradoId es obligatorio para estudiantes.");
    }

    // Actualizar campos
    usuario.setDni(request.getDni());
    usuario.setNombreCompleto(request.getNombreCompleto());
    usuario.setEmail(request.getEmail());
    usuario.setContrasena(request.getContrasena());
    usuario.setRol(rol);
    usuario.setGrado(grado); // Puede ser null para docentes
    usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);
    // Conversión de String a Date para ultimoAcceso
    if (request.getUltimoAcceso() != null && !request.getUltimoAcceso().isEmpty()) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            usuario.setUltimoAcceso(sdf.parse(request.getUltimoAcceso()));
        } catch (Exception e) {
            throw new RuntimeException("Formato de fecha inválido para ultimoAcceso. Usa yyyy-MM-dd'T'HH:mm:ss");
        }
    } else {
        usuario.setUltimoAcceso(null);
    }

    Usuario actualizado = usuarioRepository.save(usuario);
    return convertirAResponse(actualizado);
}

    // Crear usuario desde DTO
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        // Validar campos obligatorios según el rol
        RolUsuario rol = RolUsuario.valueOf(request.getRol().toUpperCase());
        
        // Solo los alumnos requieren gradoId obligatoriamente
        if (rol == RolUsuario.ALUMNO && request.getGradoId() == null) {
            throw new RuntimeException("El campo gradoId es obligatorio para estudiantes.");
        }

        // Buscar el grado existente solo si se proporciona
        Grado grado = null;
        if (request.getGradoId() != null) {
            grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("El grado especificado no existe."));
        }

        // Crear usuario sin buscar por ID
        Usuario usuario = new Usuario();
        usuario.setDni(request.getDni());
        usuario.setNombreCompleto(request.getNombreCompleto());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(request.getContrasena());
        usuario.setRol(rol);
        usuario.setGrado(grado); // Puede ser null para docentes
        usuario.setActivo(request.getActivo() != null ? request.getActivo() : true);

        // Conversión de String a Date para ultimoAcceso
        if (request.getUltimoAcceso() != null && !request.getUltimoAcceso().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                usuario.setUltimoAcceso(sdf.parse(request.getUltimoAcceso()));
            } catch (Exception e) {
                throw new RuntimeException("Formato de fecha inválido para ultimoAcceso. Usa yyyy-MM-dd'T'HH:mm:ss");
            }
        } else {
            usuario.setUltimoAcceso(null);
        }

        // Guardar usuario (ID generado automáticamente)
        Usuario guardado = usuarioRepository.save(usuario);

        // Retornar respuesta
        return convertirAResponse(guardado);
    }

    // Método auxiliar para convertir Usuario a UsuarioResponse
    private UsuarioResponse convertirAResponse(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setDni(usuario.getDni());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol().name());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setUltimoAcceso(usuario.getUltimoAcceso());
        dto.setActivo(usuario.getActivo());
        if (usuario.getGrado() != null) {
            dto.setGradoNombre(usuario.getGrado().getNombre());
        }
        return dto;
    }

    public UsuarioResponse loginDocente(String dni, String password) {
        // Buscar usuario por DNI y rol DOCENTE
        Usuario usuario = usuarioRepository.findByDniAndRol(dni, Usuario.RolUsuario.DOCENTE);
        if (usuario == null) {
            return null; // No existe usuario con ese DNI y rol
        }
        // Validar contraseña (aquí compara texto plano, idealmente usar BCrypt)
        if (!usuario.getContrasena().equals(password)) {
            return null; // Contraseña incorrecta
        }
        // Retornar DTO
        return convertirAResponse(usuario);
    }
    
    public UsuarioResponse loginAlumno(String dni, String password) {
        Usuario usuario = usuarioRepository.findByDniAndRol(dni, Usuario.RolUsuario.ALUMNO);
        if (usuario == null) {
            return null; // No existe usuario con ese DNI y rol
        }
        if (!usuario.getContrasena().equals(password)) {
            return null; // Contraseña incorrecta
        }
        return convertirAResponse(usuario);
    }
}
