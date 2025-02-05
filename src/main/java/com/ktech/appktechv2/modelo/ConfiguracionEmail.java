package com.ktech.appktechv2.modelo;

public class ConfiguracionEmail {

    private int id;
    private String correoElectronico;
    private String passwordApp;

    // Añadí el id para mejor manejo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPasswordApp() {
        return passwordApp;
    }

    public void setPasswordApp(String passwordApp) {
        this.passwordApp = passwordApp;
    }
}
