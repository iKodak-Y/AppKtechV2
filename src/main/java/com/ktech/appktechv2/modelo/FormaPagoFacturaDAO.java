package com.ktech.appktechv2.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormaPagoFacturaDAO {

    private Connection connection;

    public FormaPagoFacturaDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardarFormaPago(int idFactura, String formaPago, double valorPago) throws SQLException {
        String sql = "INSERT INTO FormaPagoFactura (id_factura, forma_pago, valor_pago) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFactura);
            stmt.setString(2, formaPago);
            stmt.setDouble(3, valorPago);
            stmt.executeUpdate();
        }
    }
}
