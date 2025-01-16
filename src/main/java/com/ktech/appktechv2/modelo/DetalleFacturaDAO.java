package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleFacturaDAO {

    /**
     * Guarda un detalle de factura en la base de datos.
     *
     * @param detalle Objeto DetalleFactura con los datos a guardar.
     * @return true si el detalle se guardó correctamente, false en caso
     * contrario.
     */
    public boolean guardarDetalle(DetalleFactura detalle) {
        String sql = "INSERT INTO DetalleFactura (id_factura, IDProducto, descripcion, cantidad, precio_unitario, "
                + "subtotal, impuestos, fecha_registro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, detalle.getIdFactura());
            pstmt.setInt(2, detalle.getIdProducto());
            pstmt.setString(3, detalle.getDescripcion());
            pstmt.setDouble(4, detalle.getCantidad());
            pstmt.setDouble(5, detalle.getPrecioUnitario());
            pstmt.setDouble(6, detalle.getSubtotal());
            pstmt.setString(7, detalle.getImpuestos());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar el detalle de la factura: " + e.getMessage());
        }
    }
}
