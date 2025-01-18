package com.ktech.appktechv2.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainLayoutController {

    @FXML
    private Label userNameLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button registerButton; // Nuevo botón para navegar al registro
    @FXML
    private HBox navBar;
    @FXML
    private StackPane contentArea;

    private String currentUser = null;
    private String currentUserRole = null;

    @FXML
    public void initialize() {
        navBar.setVisible(false);
        registerButton.setVisible(false); // Ocultar botón de registro por defecto
        updateLoginStatus();
        handleLogin(); // Cargar login al inicio
    }

    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Login.fxml"));
            Parent loginView = loader.load();

            LoginController loginController = loader.getController();
            loginController.setMainLayoutController(this);

            setContent(loginView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista de login: " + e.getMessage());
        }
    }

    public void setContent(Parent content) {
        if (contentArea != null) {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
        } else {
            System.out.println("contentArea es null");
        }
    }

    @FXML
    private void handleLogout() {
        currentUser = null;
        currentUserRole = null;
        updateLoginStatus();
        handleLogin();
    }

    public void setLoggedInUser(String username, String role) {
        this.currentUser = username;
        this.currentUserRole = role;
        updateLoginStatus();

        if ("Administrador".equals(role)) {
            registerButton.setVisible(true); // Mostrar botón de registro solo para administradores
        } else {
            registerButton.setVisible(false);
        }

        loadMainView(); // Navegar a la vista principal
    }

    private void loadMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/principal.fxml"));
            Parent mainView = loader.load();

            PrincipalController principalController = loader.getController();
            principalController.setMainLayoutController(this);

            setContent(mainView);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista principal: " + e.getMessage());
        }
    }

    private void updateLoginStatus() {
        boolean isLoggedIn = currentUser != null;
        userNameLabel.setText(isLoggedIn ? "Usuario: " + currentUser : "No has iniciado sesión");
        loginButton.setVisible(!isLoggedIn);
        logoutButton.setVisible(isLoggedIn);
        navBar.setVisible(isLoggedIn);
    }

    @FXML
    private void navigateToHome() {
        navigateToView("/com/ktech/appktechv2/vista/Principal.fxml");
    }

    @FXML
    private void navigateToClientes() {
        navigateToView("/com/ktech/appktechv2/vista/Clientes.fxml");
    }

    @FXML
    private void navigateToProductos() {
        navigateToView("/com/ktech/appktechv2/vista/Gestion_Productos.fxml");
    }

    @FXML
    private void navigateToFacturacion() {
        navigateToView("/com/ktech/appktechv2/vista/Factura.fxml");
    }

    @FXML
    private void navigateToVentas() {
        navigateToView("/com/ktech/appktechv2/vista/TicketsVentas.fxml");
    }

    @FXML
    private void navigateToReportes() {
        navigateToView("/com/ktech/appktechv2/vista/Reportes.fxml");
    }

    @FXML
    private void navigateToRegister() {
        navigateToView("/com/ktech/appktechv2/vista/Register.fxml");
    }

    private void navigateToView(String viewPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
            Parent view = loader.load();
            setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista: " + e.getMessage());
        }
    }
}
