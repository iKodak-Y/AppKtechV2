package com.ktech.appktechv2.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

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
    private Label errorLabel;

    @FXML
    private void handleRegister() {
        /* 
        // IMPLEMENTACIÓN FUTURA CON BASE DE DATOS:
        
        // 1. Validar que todos los campos estén llenos
        if (nombreField.getText().isEmpty() || usernameField.getText().isEmpty() || 
            passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            errorLabel.setText("Todos los campos son obligatorios");
            return;
        }
        
        // 2. Validar que las contraseñas coincidan
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText("Las contraseñas no coinciden");
            return;
        }
        
        try {
            Connection conn = SqlConnection.getConexion();
            
            // 3. Verificar si el usuario ya existe
            String checkQuery = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, usernameField.getText());
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                errorLabel.setText("El nombre de usuario ya está en uso");
                return;
            }
            
            // 4. Insertar nuevo usuario
            String insertQuery = "INSERT INTO usuarios (nombre_completo, username, password) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, nombreField.getText());
            insertStmt.setString(2, usernameField.getText());
            // IMPORTANTE: Hashear la contraseña antes de guardarla
            // String hashedPassword = hashPassword(passwordField.getText());
            insertStmt.setString(3, passwordField.getText());
            
            insertStmt.executeUpdate();
            
            // 5. Volver a la pantalla de login
            backToLogin();
            
        } catch (SQLException e) {
            errorLabel.setText("Error al registrar el usuario");
            e.printStackTrace();
        }
         */
    }

    @FXML
    private void backToLogin() {
        // Volver a la vista de login
    }
}
