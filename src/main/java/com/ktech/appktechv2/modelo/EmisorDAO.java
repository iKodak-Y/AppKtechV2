package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmisorDAO {

    /**
     * Recupera los datos del emisor desde la base de datos.
     *
     * @return Una instancia de Emisor con los datos cargados.
     */
    // Método para obtener el emisor por defecto
    public Emisor obtenerEmisorPorDefecto() {
        String sql = "SELECT TOP 1 * FROM Emisor";
        try (Connection con = new SqlConnection().getConexion(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Emisor emisor = new Emisor();
                emisor.setIdEmisor(rs.getInt("id_emisor"));
                emisor.setRuc(rs.getString("ruc"));
                emisor.setRazonSocial(rs.getString("razon_social"));
                emisor.setNombreComercial(rs.getString("nombre_comercial"));
                emisor.setDireccion(rs.getString("direccion"));
                emisor.setCodigoEstablecimiento(rs.getString("codigo_establecimiento"));
                emisor.setPuntoEmision(rs.getString("punto_emision"));
                emisor.setTipoAmbiente(rs.getString("tipo_ambiente"));
                emisor.setObligadoContabilidad(rs.getBoolean("obligado_contabilidad"));
                emisor.setLogo(rs.getBytes("Logo")); // Leer el logotipo como bytes
                return emisor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al recuperar los datos del emisor: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener todos los emisores
    public List<Emisor> obtenerTodosLosEmisores() {
        List<Emisor> emisores = new ArrayList<>();
        String sql = "SELECT * FROM Emisor";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emisor emisor = new Emisor();
                emisor.setIdEmisor(rs.getInt("id_emisor"));
                emisor.setRuc(rs.getString("ruc"));
                emisor.setRazonSocial(rs.getString("razon_social"));
                emisor.setNombreComercial(rs.getString("nombre_comercial"));
                emisor.setDireccion(rs.getString("direccion"));
                emisor.setCodigoEstablecimiento(rs.getString("codigo_establecimiento"));
                emisor.setPuntoEmision(rs.getString("punto_emision"));
                emisor.setTipoAmbiente(rs.getString("tipo_ambiente"));
                emisores.add(emisor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al recuperar los datos de los emisores: " + e.getMessage());
        }
        return emisores;
    }

    // Método para obtener un emisor por código de establecimiento
    public Emisor obtenerEmisorPorCodigoEstablecimiento(String codigoEstablecimiento) {
        String sql = "SELECT * FROM Emisor WHERE codigo_establecimiento = ?";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, codigoEstablecimiento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Emisor emisor = new Emisor();
                    emisor.setIdEmisor(rs.getInt("id_emisor"));
                    emisor.setRuc(rs.getString("ruc"));
                    emisor.setRazonSocial(rs.getString("razon_social"));
                    emisor.setNombreComercial(rs.getString("nombre_comercial"));
                    emisor.setDireccion(rs.getString("direccion"));
                    emisor.setCodigoEstablecimiento(rs.getString("codigo_establecimiento"));
                    emisor.setPuntoEmision(rs.getString("punto_emision"));
                    emisor.setTipoAmbiente(rs.getString("tipo_ambiente"));
                    return emisor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al recuperar los datos del emisor por código de establecimiento: " + e.getMessage());
        }
        return null;
    }
}
