package com.runasimi_edu.backend.dto.request;


import lombok.Data;

@Data
public class UsuarioRequest {
    private String dni;
    private String nombreCompleto;
    private String email;
    private String contrasena;
    private String rol;          // "ALUMNO", "DOCENTE" o "ADMIN"
    private Long gradoId;
    private String ultimoAcceso; // Cambiado a String para evitar problemas de formato
    private Boolean activo = true;

    public UsuarioRequest() {}

    public UsuarioRequest(String contrasena, String dni, String email, Long gradoId, String nombreCompleto, String rol, String ultimoAcceso) {
        this.contrasena = contrasena;
        this.dni = dni;
        this.email = email;
        this.gradoId = gradoId;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.ultimoAcceso = ultimoAcceso;
    }
}