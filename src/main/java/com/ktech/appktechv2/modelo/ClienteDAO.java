package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());
    private Connection con;  // Declaración de la conexión

    public ClienteDAO() {
        // Inicialización de la conexión usando tu clase SqlConnection
        SqlConnection sqlConnection = new SqlConnection();
        this.con = sqlConnection.getConexion();
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Clientes SET Nombre = ?, Apellido = ?, CedulaRUC = ?, Direccion = ?, Telefono = ?, Email = ?, Sexo = ?, Estado = ? WHERE IDCliente = ?";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getCedulaRUC());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getEmail());
            pstmt.setString(7, String.valueOf(cliente.getSexo()));
            pstmt.setString(8, String.valueOf(cliente.getEstado()));
            pstmt.setInt(9, cliente.getIdCliente());

            int affectedRows = pstmt.executeUpdate();
            con.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar el cliente", e);
            try {
                if (con != null && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error al realizar rollback", rollbackEx);
            }
            return false;
        }
    }

    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes WHERE Estado = 'A'";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("IDCliente"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setCedulaRUC(rs.getString("CedulaRUC"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setTelefono(rs.getString("Telefono"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setSexo(rs.getString("Sexo").charAt(0));
                cliente.setEstado(rs.getString("Estado").charAt(0));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener los clientes", e);
        }

        return clientes;
    }

    public Cliente buscarPorCedulaONombre(String termino) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Clientes WHERE (CedulaRUC = ? OR Nombre = ? OR Apellido = ?) AND Estado = 'A'";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, termino);
            pstmt.setString(2, termino);
            pstmt.setString(3, termino);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("IDCliente"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setCedulaRUC(rs.getString("CedulaRUC"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setEmail(rs.getString("Email"));
                    cliente.setSexo(rs.getString("Sexo").charAt(0));
                    cliente.setEstado(rs.getString("Estado").charAt(0));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar cliente por cédula o nombre", e);
        }

        return cliente;
    }

    public boolean guardar(Cliente cliente) {
        String sql = "INSERT INTO Clientes (Nombre, Apellido, CedulaRUC, Direccion, Telefono, Email, Sexo, Estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getCedulaRUC());
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getEmail());
            pstmt.setString(7, String.valueOf(cliente.getSexo()));
            pstmt.setString(8, String.valueOf(cliente.getEstado()));

            int affectedRows = pstmt.executeUpdate();
            con.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar el cliente", e);
            try {
                if (con != null && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error al realizar rollback", rollbackEx);
            }
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "UPDATE Clientes SET Estado = 'I' WHERE IDCliente = ?";

        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            con.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar el cliente", e);
            try {
                if (con != null && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Error al realizar rollback", rollbackEx);
            }
            return false;
        }
    }
}
