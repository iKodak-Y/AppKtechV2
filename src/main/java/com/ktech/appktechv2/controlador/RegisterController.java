package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import com.ktech.appktechv2.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;

public class RegisterController {

    @FXML
    private TextField nombreField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private ComboBox<String> rolComboBox;
    @FXML
    private Label errorLabel;

    private MainLayoutController mainLayoutController;

    public void initialize() {
        loadRoles();
    }

    private void loadRoles() {
        try (Connection conn = new SqlConnection().getConexion()) {
            String query = "SELECT nombre_rol FROM Roles ORDER BY id_rol";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                rolComboBox.getItems().clear();
                while (rs.next()) {
                    rolComboBox.getItems().add(rs.getString("nombre_rol"));
                }
            }
        } catch (SQLException e) {
            errorLabel.setText("Error al cargar roles.");
            e.printStackTrace();
        }
    }

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    @FXML
    private void handleRegister() {
        if (!validateFields()) {
            return;
        }

        try (Connection conn = new SqlConnection().getConexion()) {
            conn.setAutoCommit(false);
            try {
                // Primero verificar si el usuario existe
                if (userExists(conn, usernameField.getText())) {
                    errorLabel.setText("El nombre de usuario ya está en uso.");
                    return;
                }

                // Obtener el ID del rol seleccionado
                int idRol = getRolId(conn, rolComboBox.getValue());
                if (idRol == -1) {
                    errorLabel.setText("Error al obtener el rol.");
                    return;
                }

                // Insertar el nuevo usuario
                String insertQuery = """
                            INSERT INTO Usuarios (nombre_completo, username, password, id_rol)
                            VALUES (?, ?, ?, ?)
                        """;

                try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, nombreField.getText());
                    insertStmt.setString(2, usernameField.getText());
                    insertStmt.setString(3, PasswordUtil.hashPassword(passwordField.getText()));
                    insertStmt.setInt(4, idRol);
                    insertStmt.executeUpdate();
                }

                conn.commit();
                errorLabel.setText("Registro exitoso. Puede iniciar sesión.");

            } catch (SQLException e) {
                conn.rollback();
                errorLabel.setText("Error al registrar el usuario.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            errorLabel.setText("Error de conexión.");
            e.printStackTrace();
        }
    }

    private void loadView(String fxmlPath) {
        if (mainLayoutController == null) {
            System.out.println("MainLayoutController no está configurado");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            mainLayoutController.setContent(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (nombreField.getText().isEmpty() || usernameField.getText().isEmpty()
                || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()
                || rolComboBox.getValue() == null) {
            errorLabel.setText("Todos los campos son obligatorios.");
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText("Las contraseñas no coinciden.");
            return false;
        }

        return true;
    }

    private boolean userExists(Connection conn, String username) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM Usuarios WHERE username = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private int getRolId(Connection conn, String nombreRol) throws SQLException {
        String query = "SELECT id_rol FROM Roles WHERE nombre_rol = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombreRol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_rol");
                }
            }
        }
        return -1;
    }

    @FXML
    private void backToConfig(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/Config.fxml");
    }
}
