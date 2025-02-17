package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import com.ktech.appktechv2.util.PasswordUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button registerButton;

    private MainLayoutController mainLayoutController;

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }

    @FXML
    public void initialize() {
        checkAdminExistence();

        // Configurar evento Enter para el campo de usuario
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

        // Configurar evento Enter para el campo de contraseña
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin();
            }
        });
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = new SqlConnection().getConexion()) {
            String query = """
                        SELECT u.id_usuario, u.nombre_completo, u.username,
                               r.id_rol, r.nombre_rol
                        FROM Usuarios u
                        JOIN Roles r ON u.id_rol = r.id_rol
                        WHERE u.username = ? AND u.password = ?
                    """;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, PasswordUtil.hashPassword(password));

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String nombreCompleto = rs.getString("nombre_completo");
                        String rol = rs.getString("nombre_rol");
                        int idUsuario = rs.getInt("id_usuario");

                        mainLayoutController.setLoggedInUser(nombreCompleto, rol, idUsuario);
                        errorLabel.setText("");
                    } else {
                        errorLabel.setText("Credenciales incorrectas.");
                    }
                }
            }
        } catch (SQLException e) {
            errorLabel.setText("Error al intentar iniciar sesión.");
            e.printStackTrace();
        }
    }

    private void checkAdminExistence() {
        try (Connection conn = new SqlConnection().getConexion()) {
            String query = """
                        SELECT COUNT(*)
                        FROM Usuarios u
                        JOIN Roles r ON u.id_rol = r.id_rol
                        WHERE r.nombre_rol = 'Administrador'
                    """;

            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    registerButton.setVisible(true);
                } else {
                    registerButton.setVisible(false);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar administradores: " + e.getMessage());
        }
    }

    @FXML
    private void navigateToRegister() {
        loadView("/com/ktech/appktechv2/vista/Register.fxml");

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
}
