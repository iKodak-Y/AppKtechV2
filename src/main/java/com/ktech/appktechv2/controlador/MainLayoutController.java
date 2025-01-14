package com.ktech.appktechv2.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import javafx.event.ActionEvent;

public class MainLayoutController {

    @FXML
    private Label userNameLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button logoutButton;
    @FXML
    private HBox navBar;
    @FXML
    private StackPane contentArea;

    private String currentUser = null;

    @FXML
    private void initialize() {
        navBar.setVisible(false);
        updateLoginStatus();
        // Cargar la vista de login automáticamente al inicio
        handleLogin();
    }

    @FXML
    private void handleLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Login.fxml"));
            Parent loginView = loader.load();

            // Obtener el controlador y establecer la referencia
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
        updateLoginStatus();
        handleLogin();
    }

    public void setLoggedInUser(String username) {
        this.currentUser = username;
        updateLoginStatus();
        // Aquí podrías cargar la vista principal después del login
        loadMainView();
    }

    private void loadMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/principal.fxml"));
            Parent mainView = loader.load();
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

    // Métodos de navegación
    @FXML
    private void navigateToHome() {
        if (currentUser == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Principal.fxml"));
            setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToClientes() {
        if (currentUser == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Clientes.fxml"));
            setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Implementar los demás métodos de navegación de manera similar
    @FXML
    private void navigateToProductos(ActionEvent event) {
        if (currentUser == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Gestion_Productos.fxml"));
            setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToFacturacion(ActionEvent event) {
        if (currentUser == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Factura.fxml"));
            setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToVentas(ActionEvent event) {
        if (currentUser == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/TicketsVentas.fxml"));
            setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToReportes(ActionEvent event) {
    }
}
