package com.ktech.appktechv2.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private MainLayoutController mainLayoutController;

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }

    @FXML
    public void initialize() {
        // Asegurarse de que el errorLabel está vacío al inicio
        if (errorLabel != null) {
            errorLabel.setText("");
        }
    }

    @FXML
    private void handleLogin() {
        if (mainLayoutController == null) {
            System.out.println("mainLayoutController es null");
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Intento de login - Usuario: " + username);

        // Validación con las credenciales especificadas
        if (username.equals("isaac") && password.equals("admin")) {
            System.out.println("Login exitoso");
            mainLayoutController.setLoggedInUser(username);
            if (errorLabel != null) {
                errorLabel.setText("");
            }
        } else {
            System.out.println("Login fallido");
            if (errorLabel != null) {
                errorLabel.setText("Usuario o contraseña incorrectos");
            }
        }
    }
}
