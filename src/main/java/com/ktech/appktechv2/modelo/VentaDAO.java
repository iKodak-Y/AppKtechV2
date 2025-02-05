package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;
import java.sql.*;
import java.util.List;

public class VentaDAO {

    public boolean registrarVenta(Venta venta) {
        String sqlVenta = "INSERT INTO Ventas (IdCliente, id_emisor, Fecha, Total, Estado, NumeroSecuencial, RucComprador, RazonSocialComprador, DireccionComprador, FechaEmision) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO DetalleVenta (IdVenta, IdProducto, Cantidad, PrecioUnitario, Subtotal, IVA, Total) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = new SqlConnection().getConexion()) {
            con.setAutoCommit(false);

            // Insertar la Venta
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS)) {
                psVenta.setInt(1, venta.getIdCliente());
                psVenta.setInt(2, venta.getIdEmisor());
                psVenta.setTimestamp(3, Timestamp.valueOf(venta.getFecha()));
                psVenta.setDouble(4, venta.getTotal());
                psVenta.setString(5, venta.getEstado());
                psVenta.setString(6, venta.getNumeroSecuencial());
                psVenta.setString(7, venta.getRucComprador());
                psVenta.setString(8, venta.getRazonSocialComprador());
                psVenta.setString(9, venta.getDireccionComprador());
                psVenta.setTimestamp(10, Timestamp.valueOf(venta.getFechaEmision()));

                if (psVenta.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }

                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        venta.setIdVenta(rs.getInt(1));
                    } else {
                        con.rollback();
                        return false;
                    }
                }
            }

            // Insertar Detalles de Venta
            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                for (DetalleVenta detalle : venta.getDetalles()) {
                    psDetalle.setInt(1, venta.getIdVenta());
                    psDetalle.setInt(2, detalle.getIdProducto());
                    psDetalle.setInt(3, detalle.getCantidad());
                    psDetalle.setDouble(4, detalle.getPrecioUnitario());
                    psDetalle.setDouble(5, detalle.getSubtotal());
                    psDetalle.setDouble(6, detalle.getIva());
                    psDetalle.setDouble(7, detalle.getTotal());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVenta(int idVenta) {
        String sqlEliminarDetalles = "DELETE FROM DetalleVenta WHERE IDVenta = ?";
        String sqlEliminarVenta = "DELETE FROM Ventas WHERE IDVenta = ?";
        try (Connection con = new SqlConnection().getConexion()) {
            con.setAutoCommit(false);

            // Eliminar los detalles de la venta
            try (PreparedStatement psDetalles = con.prepareStatement(sqlEliminarDetalles)) {
                psDetalles.setInt(1, idVenta);
                psDetalles.executeUpdate();
            }

            // Eliminar la venta principal
            try (PreparedStatement psVenta = con.prepareStatement(sqlEliminarVenta)) {
                psVenta.setInt(1, idVenta);
                int rowsAffected = psVenta.executeUpdate();

                if (rowsAffected == 0) {
                    con.rollback(); // Revertir si no se eliminó ninguna fila
                    return false;
                }
            }

            con.commit(); // Confirmar la transacción
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
