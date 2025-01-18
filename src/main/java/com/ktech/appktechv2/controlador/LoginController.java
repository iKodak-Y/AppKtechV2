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
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = new SqlConnection().getConexion()) {
            String query = "SELECT nombre_completo, rol, password FROM Usuarios WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        String nombreCompleto = rs.getString("nombre_completo");
                        String rol = rs.getString("rol");

                        if (PasswordUtil.hashPassword(password).equals(storedPassword)) {
                            mainLayoutController.setLoggedInUser(nombreCompleto, rol);
                            errorLabel.setText("");
                        } else {
                            errorLabel.setText("Contraseña incorrecta.");
                        }
                    } else {
                        errorLabel.setText("Usuario no encontrado.");
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
            String query = "SELECT COUNT(*) FROM Usuarios WHERE rol = 'Administrador'";
            try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    registerButton.setVisible(true); // Mostrar botón si no hay admin
                } else {
                    registerButton.setVisible(false); // Ocultar botón si hay admin
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
