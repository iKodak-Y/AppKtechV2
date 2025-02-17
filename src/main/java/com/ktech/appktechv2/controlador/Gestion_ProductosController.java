package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.Producto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Gestion_ProductosController implements Initializable {
    @FXML
    private StackPane contentArea;
    @FXML
    private Button btnInicio;
    @FXML
    private Button btnRegistrar;
    @FXML
    private Button btnCargaMasiva;
    private MainLayoutController mainLayoutController;
    private TablaProductosController tablaProductosController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // No configuración adicional necesaria
    }

    public void cargarVistaTablaProductos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/TablaProductos.fxml"));
            Node tablaProductosView = loader.load();

            tablaProductosController = loader.getController();
            tablaProductosController.setContentArea(contentArea);
            tablaProductosController.setGestionProductosController(this);
            tablaProductosController.setMainLayoutController(mainLayoutController); // Añadir esta línea

            contentArea.getChildren().clear();
            contentArea.getChildren().add(tablaProductosView);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de la tabla de productos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void cargarVistaActualizarProducto(Producto producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/ActualizarProducto.fxml"));
            Node actualizarView = loader.load();

            ActualizarProductoController actualizarController = loader.getController();
            actualizarController.setContentArea(contentArea);
            actualizarController.setGestionProductosController(this);
            actualizarController.setProductoSeleccionado(producto);

            contentArea.getChildren().clear();
            contentArea.getChildren().add(actualizarView);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de actualización: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void volverATablaProductos() {
        if (tablaProductosController != null) {
            tablaProductosController.recargarDatos();
        }
        cargarVistaTablaProductos();
    }

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }

    @FXML
    private void volverInicio(ActionEvent event) {
        cargarVistaTablaProductos();
    }


    @FXML
    public void abrirRegistroProducto(ActionEvent actionEvent) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/com/ktech/appktechv2/vista/RegistroProducto.fxml"));
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista para registrar producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    private void abrirCargaMasiva(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/CargaMasivaProductos.fxml"));
            Node node = loader.load();
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de carga masiva: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}