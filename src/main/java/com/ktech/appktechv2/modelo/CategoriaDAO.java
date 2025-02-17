package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> obtenerTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";

        try (Connection con = new SqlConnection().getConexion();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("IDCategoria"));
                categoria.setNombre(rs.getString("Nombre"));
                categoria.setEstado(rs.getString("Estado").charAt(0));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    public Categoria obtenerPorId(int idCategoria) {
        String sql = "SELECT * FROM Categorias WHERE IDCategoria = ?";
        try (Connection con = new SqlConnection().getConexion(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idCategoria);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("IDCategoria"));
                    categoria.setNombre(rs.getString("Nombre"));
                    categoria.setEstado(rs.getString("Estado").charAt(0));
                    return categoria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean guardar(Categoria categoria) {
        String sql = "INSERT INTO Categorias (Nombre, Estado) VALUES (?, ?)";

        try (Connection con = new SqlConnection().getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, String.valueOf(categoria.getEstado()));

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE Categorias SET Nombre = ?, Estado = ? WHERE IDCategoria = ?";

        try (Connection con = new SqlConnection().getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, String.valueOf(categoria.getEstado()));
            pstmt.setInt(3, categoria.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Categoria obtenerPorNombre(String nombreCategoria) {
        String sql = "SELECT * FROM Categorias WHERE Nombre = ?";
        try (Connection con = new SqlConnection().getConexion();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombreCategoria);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getInt("IDCategoria"));
                    categoria.setNombre(rs.getString("Nombre"));
                    categoria.setEstado(rs.getString("Estado").charAt(0));
                    return categoria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}