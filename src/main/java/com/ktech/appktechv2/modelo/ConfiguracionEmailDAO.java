package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;
import java.sql.*;

public class ConfiguracionEmailDAO {

    private SqlConnection conexion;

    public ConfiguracionEmailDAO() {
        this.conexion = new SqlConnection();
    }

    public boolean actualizarConfiguracion(String correo, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion.getConexion();

            // Primero verificamos si existe algún registro
            String checkSql = "SELECT COUNT(*) FROM ConfiguracionEmail WHERE activo = 1";
            stmt = conn.prepareStatement(checkSql);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                // Si no existe, insertamos
                String insertSql = "INSERT INTO ConfiguracionEmail (correo_electronico, password_app) VALUES (?, ?)";
                stmt = conn.prepareStatement(insertSql);
                stmt.setString(1, correo);
                stmt.setString(2, password);
            } else {
                // Si existe, actualizamos
                String updateSql = "UPDATE ConfiguracionEmail SET correo_electronico = ?, password_app = ?, fecha_modificacion = GETDATE() WHERE activo = 1";
                stmt = conn.prepareStatement(updateSql);
                stmt.setString(1, correo);
                stmt.setString(2, password);
            }

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ConfiguracionEmail obtenerConfiguracionActual() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = conexion.getConexion();
            String sql = "SELECT id, correo_electronico, password_app FROM ConfiguracionEmail WHERE activo = 1";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ConfiguracionEmail config = new ConfiguracionEmail();
                config.setId(rs.getInt("id"));
                config.setCorreoElectronico(rs.getString("correo_electronico"));
                config.setPasswordApp(rs.getString("password_app"));
                return config;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
