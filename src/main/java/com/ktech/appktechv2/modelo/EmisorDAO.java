package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmisorDAO {

    /**
     * Recupera los datos del emisor desde la base de datos.
     *
     * @return Una instancia de Emisor con los datos cargados.
     */
    public Emisor obtenerEmisorPorDefecto() {
        String sql = "SELECT TOP 1 * FROM Emisor"; // Ajusta si necesitas filtros adicionales

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
                return emisor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al recuperar los datos del emisor: " + e.getMessage());
        }
        return null; // Si no se encuentra un emisor
    }
}
