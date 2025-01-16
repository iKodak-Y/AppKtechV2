package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    /**
     * Guarda una factura en la base de datos.
     *
     * @param factura Objeto Factura con los datos a guardar.
     * @return true si la factura se guardó correctamente, false en caso
     * contrario.
     */
    public boolean guardarFactura(Factura factura) {
        String sql = "INSERT INTO FacturaElectronica (id_emisor, clave_acceso, numero_secuencial, fecha_emision, "
                + "ruc_comprador, razon_social_comprador, direccion_comprador, estado, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, factura.getIdEmisor());
            pstmt.setString(2, factura.getClaveAcceso());
            pstmt.setString(3, factura.getNumeroSecuencial());
            pstmt.setDate(4, new java.sql.Date(factura.getFechaEmision().getTime()));
            pstmt.setString(5, factura.getRucComprador());
            pstmt.setString(6, factura.getRazonSocialComprador());
            pstmt.setString(7, factura.getDireccionComprador());
            pstmt.setString(8, factura.getEstado());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        factura.setIdFactura(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar la factura: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene el siguiente número secuencial para un emisor.
     *
     * @param idEmisor ID del emisor.
     * @return El siguiente número secuencial como String.
     */
    public String obtenerSiguienteSecuencial(int idEmisor) {
        String sql = "{CALL ObtenerSiguienteSecuencial(?, ?)}";

        try (Connection con = new SqlConnection().getConexion(); CallableStatement stmt = con.prepareCall(sql)) {

            stmt.setInt(1, idEmisor);
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

            stmt.execute();
            return stmt.getString(2); // Devuelve el valor del parámetro de salida
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el siguiente secuencial: " + e.getMessage());
        }
    }

    /**
     * Obtiene una factura por su ID.
     *
     * @param idFactura ID de la factura.
     * @return Una instancia de Factura o null si no se encuentra.
     */
    public Factura obtenerFacturaPorId(int idFactura) {
        String sql = "SELECT * FROM FacturaElectronica WHERE id_factura = ?";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, idFactura);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Factura factura = new Factura();
                    factura.setIdFactura(rs.getInt("id_factura"));
                    factura.setIdEmisor(rs.getInt("id_emisor"));
                    factura.setClaveAcceso(rs.getString("clave_acceso"));
                    factura.setNumeroSecuencial(rs.getString("numero_secuencial"));
                    factura.setFechaEmision(rs.getDate("fecha_emision"));
                    factura.setRucComprador(rs.getString("ruc_comprador"));
                    factura.setRazonSocialComprador(rs.getString("razon_social_comprador"));
                    factura.setDireccionComprador(rs.getString("direccion_comprador"));
                    factura.setEstado(rs.getString("estado"));
                    factura.setFechaAutorizacion(rs.getDate("fecha_autorizacion"));
                    factura.setXmlAutorizado(rs.getString("xml_autorizado"));
                    return factura;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la factura por ID: " + e.getMessage());
        }
        return null;
    }
}
