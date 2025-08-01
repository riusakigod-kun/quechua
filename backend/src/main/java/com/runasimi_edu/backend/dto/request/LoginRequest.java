package com.runasimi_edu.backend.dto.request;

public class LoginRequest {
    private String dni;
    private String password;

    // Getters y setters
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
