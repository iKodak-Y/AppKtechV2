package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.Categoria;
import com.ktech.appktechv2.modelo.CategoriaDAO;
import com.ktech.appktechv2.modelo.Producto;
import com.ktech.appktechv2.modelo.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActualizarProductoController implements Initializable {
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtPvp;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtIva;
    @FXML
    private ComboBox<Categoria> comboCategoria;

    private ProductoDAO productoDAO;
    private Producto productoSeleccionado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnActualizar;

    private StackPane contentArea;

    public void setContentArea(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    private Gestion_ProductosController gestionProductosController;

    public void setGestionProductosController(Gestion_ProductosController controller) {
        this.gestionProductosController = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productoDAO = new ProductoDAO();
        cargarCategorias();
        agregarListeners();
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        comboCategoria.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerTodas()));
    }

    public void setProductoSeleccionado(Producto producto) {
        this.productoSeleccionado = producto;
        limpiarCampos();
        txtCodigo.setText(producto.getCodigo());
        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtPvp.setText(String.valueOf(producto.getPvp()));
        txtStock.setText(String.valueOf(producto.getStockActual()));
        txtIva.setText(String.valueOf(producto.getIva()));
        comboCategoria.getSelectionModel().select(producto.getCategoria());
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtPvp.clear();
        txtStock.clear();
        txtIva.clear();
        comboCategoria.getSelectionModel().clearSelection();
    }

    @FXML
    private void actualizarProducto() {
        if (!validarCampos()) {
            return;
        }

        try {
            productoSeleccionado.setCodigo(txtCodigo.getText());
            productoSeleccionado.setNombre(txtNombre.getText());
            productoSeleccionado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            productoSeleccionado.setPvp(Double.parseDouble(txtPvp.getText()));
            productoSeleccionado.setStockActual(Integer.parseInt(txtStock.getText()));
            productoSeleccionado.setIva(Double.parseDouble(txtIva.getText()));
            productoSeleccionado.setCategoria(comboCategoria.getSelectionModel().getSelectedItem());

            if (productoDAO.actualizar(productoSeleccionado)) {
                mostrarAlerta("Éxito", "Producto actualizado correctamente", Alert.AlertType.INFORMATION);
                if (gestionProductosController != null) {
                    gestionProductosController.volverATablaProductos();
                }
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el producto", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar el producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void agregarListeners() {
        txtCodigo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(productoSeleccionado.getCodigo())) {
                txtCodigo.setStyle("-fx-font-weight: bold;");
            } else {
                txtCodigo.setStyle("");
            }
        });

        txtNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(productoSeleccionado.getNombre())) {
                txtNombre.setStyle("-fx-font-weight: bold;");
            } else {
                txtNombre.setStyle("");
            }
        });

        txtPrecio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(String.valueOf(productoSeleccionado.getPrecio()))) {
                txtPrecio.setStyle("-fx-font-weight: bold;");
            } else {
                txtPrecio.setStyle("");
            }
        });

        txtPvp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(String.valueOf(productoSeleccionado.getPvp()))) {
                txtPvp.setStyle("-fx-font-weight: bold;");
            } else {
                txtPvp.setStyle("");
            }
        });

        txtStock.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(String.valueOf(productoSeleccionado.getStockActual()))) {
                txtStock.setStyle("-fx-font-weight: bold;");
            } else {
                txtStock.setStyle("");
            }
        });

        txtIva.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(String.valueOf(productoSeleccionado.getIva()))) {
                txtIva.setStyle("-fx-font-weight: bold;");
            } else {
                txtIva.setStyle("");
            }
        });

        comboCategoria.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(productoSeleccionado.getCategoria())) {
                comboCategoria.setStyle("-fx-font-weight: bold;");
            } else {
                comboCategoria.setStyle("");
            }
        });
    }

    @FXML
    private void cancelarActualizacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cancelación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea cancelar la actualización? Se perderán todos los cambios no guardados.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if (gestionProductosController != null) {
                gestionProductosController.volverATablaProductos();
            }
        }
    }

    private void regresarAVistaInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/TablaProductos.fxml"));
            Node node = loader.load();
            contentArea.getChildren().setAll(node);
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista de inicio: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                txtPrecio.getText().isEmpty() || txtPvp.getText().isEmpty() ||
                txtStock.getText().isEmpty() || txtIva.getText().isEmpty() ||
                comboCategoria.getSelectionModel().isEmpty()) {

            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Double.parseDouble(txtPrecio.getText());
            Double.parseDouble(txtPvp.getText());
            Integer.parseInt(txtStock.getText());
            Double.parseDouble(txtIva.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Formato de número inválido en uno de los campos", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}