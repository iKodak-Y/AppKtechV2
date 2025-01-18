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

    @FXML
    public void initialize() {
        rolComboBox.getItems().addAll("Administrador", "Trabajador");
    }

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    @FXML
    private void handleRegister() {
        if (nombreField.getText().isEmpty() || usernameField.getText().isEmpty()
                || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()
                || rolComboBox.getValue() == null) {
            errorLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        try (Connection conn = new SqlConnection().getConexion()) {
            String checkQuery = "SELECT COUNT(*) FROM Usuarios WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, usernameField.getText());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        errorLabel.setText("El nombre de usuario ya está en uso.");
                        return;
                    }
                }
            }

            String hashedPassword = PasswordUtil.hashPassword(passwordField.getText());
            String insertQuery = "INSERT INTO Usuarios (nombre_completo, username, password, rol) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, nombreField.getText());
                insertStmt.setString(2, usernameField.getText());
                insertStmt.setString(3, hashedPassword);
                insertStmt.setString(4, rolComboBox.getValue());
                insertStmt.executeUpdate();
                errorLabel.setText("Registro exitoso. Puede iniciar sesión.");
            }
        } catch (SQLException e) {
            errorLabel.setText("Error al registrar el usuario.");
            e.printStackTrace();
        }
    }

    @FXML
    private void backToLogin() {
        loadView("/com/ktech/appktechv2/vista/Login.fxml");

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
