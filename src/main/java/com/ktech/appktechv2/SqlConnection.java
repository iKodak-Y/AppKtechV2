package com.ktech.appktechv2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    // Cambia los datos de conexión según tus necesidades
    private static final String URL = "jdbc:sqlserver://IKODAK;database=BD_KTECH_V2;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin";

    public Connection getConexion() {
        try {
            // Establecer la conexión
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
            return con;
        } catch (SQLException ex) {
            System.err.println("Error al conectar a la base de datos: " + ex.getMessage());
            return null;
        }
    }
}
