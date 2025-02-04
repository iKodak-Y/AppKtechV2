package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
    private String currentUserRole = null;
    @FXML
    private BorderPane mainLayout;
    @FXML
    private Label businessNameLabel; // Este es el label donde se mostrará el nombre del negocio
    @FXML
    private ImageView logoImageView;
    @FXML
    private Button homeButton;
    @FXML
    private Button clientesButton;
    @FXML
    private Button productosButton;
    @FXML
    private Button facturacionButton;
    @FXML
    private Button ventasButton;
    @FXML
    private Button reportesButton;
    @FXML
    private Button registerEmisorButton;
    @FXML
    private Button registerUserButton;

    public void initialize() {
        navBar.setVisible(false);
        registerUserButton.setVisible(false); // Ocultar botón de registro por defecto
        updateLoginStatus();
        handleLogin(); // Cargar login al inicio

        // Mostrar el nombre del negocio al iniciar la aplicación
        businessNameLabel.setText(obtenerNombreComercial());
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
            registerUserButton.setVisible(true); // Mostrar botón de registro solo para administradores
            registerEmisorButton.setVisible(true);
        } else {
            registerUserButton.setVisible(false);
            registerEmisorButton.setVisible(false);
        }

        loadMainView(); // Navegar a la vista principal
        actualizarNombreComercial();
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
        setActiveButton(homeButton);
        navigateToView("/com/ktech/appktechv2/vista/Principal.fxml");
    }

    @FXML
    private void navigateToClientes() {
        setActiveButton(clientesButton);
        navigateToView("/com/ktech/appktechv2/vista/Clientes.fxml");
    }

    @FXML
    private void navigateToProductos() {
        setActiveButton(productosButton);
        navigateToView("/com/ktech/appktechv2/vista/Gestion_Productos.fxml");
    }

    @FXML
    private void navigateToFacturacion() {
        setActiveButton(facturacionButton);
        navigateToView("/com/ktech/appktechv2/vista/Factura.fxml");
    }

    @FXML
    private void navigateToVentas() {
        setActiveButton(ventasButton);
        navigateToView("/com/ktech/appktechv2/vista/TicketsVentas.fxml");
    }

    @FXML
    private void navigateToReportes() {
        setActiveButton(reportesButton);
        navigateToView("/com/ktech/appktechv2/vista/Reportes.fxml");
    }

    @FXML
    private void navigateToRegister() {
        setActiveButton(registerUserButton);
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

    @FXML
    private void navigateToRegisterEmisor(ActionEvent event) {
        setActiveButton(registerEmisorButton);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Registro_Emisor.fxml"));
            Parent view = loader.load();

            // Establecer la referencia del MainLayoutController en Registro_EmisorController
            Registro_EmisorController emisorController = loader.getController();
            emisorController.setMainLayoutController(this);

            setContent(view);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error cargando vista de registro de emisor: " + e.getMessage());
        }
    }

    private String obtenerNombreComercial() {
        String nombreComercial = "Nombre no registrado"; // Texto por defecto si no hay un nombre registrado
        byte[] logoBytes = null; // Bytes del logotipo

        // Crear una instancia de la clase SqlConnection
        SqlConnection sqlConnection = new SqlConnection();
        Connection conn = sqlConnection.getConexion();
        if (conn != null) {
            String sql = "SELECT nombre_comercial, Logo FROM Emisor WHERE id_emisor = 1"; // Ajusta según tu lógica
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nombre = rs.getString("nombre_comercial");
                    if (nombre != null && !nombre.isEmpty()) {
                        nombreComercial = nombre;
                    }
                    // Recuperar el logotipo como bytes
                    logoBytes = rs.getBytes("Logo");
                }
            } catch (SQLException e) {
                System.err.println("Error al obtener el nombre comercial o el logotipo: " + e.getMessage());
            } finally {
                try {
                    conn.close(); // Cerrar la conexión después de usarla
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }

        // Actualizar el logotipo en la interfaz
        if (logoBytes != null) {
            Image logoImage = new Image(new ByteArrayInputStream(logoBytes));
            logoImageView.setImage(logoImage);
        } else {
            logoImageView.setImage(null); // Limpiar el ImageView si no hay logotipo
        }

        return nombreComercial;
    }

    public void actualizarNombreComercial() {
        businessNameLabel.setText(obtenerNombreComercial());
    }

    private void setActiveButton(Button activeButton) {
        // Lista de todos los botones de navegación
        Button[] buttons = {
            homeButton,
            clientesButton,
            productosButton,
            facturacionButton,
            ventasButton,
            reportesButton,
            registerEmisorButton,
            registerUserButton
        };

        // Remover la clase 'active' de todos los botones
        for (Button button : buttons) {
            button.getStyleClass().remove("active");
        }

        // Agregar la clase 'active' al botón seleccionado
        if (activeButton != null) {
            activeButton.getStyleClass().add("active");
        }
    }
}
