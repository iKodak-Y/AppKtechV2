package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.Categoria;
import com.ktech.appktechv2.modelo.CategoriaDAO;
import com.ktech.appktechv2.modelo.Producto;
import com.ktech.appktechv2.modelo.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroProductoController implements Initializable {
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
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, String> colCodigo;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Double> colPvp;
    @FXML
    private TableColumn<Producto, Integer> colStock;
    @FXML
    private TableColumn<Producto, Double> colIva;
    @FXML
    private TableColumn<Producto, String> colCategoria;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private ObservableList<Producto> listaProductos;
    private ProductoDAO productoDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaProductos = FXCollections.observableArrayList();
        productoDAO = new ProductoDAO();
        configurarTabla();
        cargarCategorias();
        agregarTooltips();

        // Agregar listener para doble clic en la tabla para eliminar producto
        tablaProductos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
                if (productoSeleccionado != null) {
                    if (confirmarEliminacion()) {
                        listaProductos.remove(productoSeleccionado);
                    }
                }
            }
        });
    }

    private void configurarTabla() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPvp.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockActual"));
        colIva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        colCategoria.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getCategoria() != null ?
                                cellData.getValue().getCategoria().getNombre() : ""
                )
        );
        tablaProductos.setItems(listaProductos);
    }

    private void cargarCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        comboCategoria.setItems(FXCollections.observableArrayList(categoriaDAO.obtenerTodas()));
    }

    private void agregarTooltips() {
        txtCodigo.setTooltip(new Tooltip("Ejemplo: P001"));
        txtNombre.setTooltip(new Tooltip("Ejemplo: Producto A"));
        txtPrecio.setTooltip(new Tooltip("Ejemplo: 10.50"));
        txtPvp.setTooltip(new Tooltip("Ejemplo: 12.00"));
        txtStock.setTooltip(new Tooltip("Ejemplo: 100"));
        txtIva.setTooltip(new Tooltip("Ejemplo: 0.15"));
    }

    @FXML
    private void agregarProducto() {
        if (!validarCampos()) {
            return;
        }

        // Verificar si el código ya existe en la tabla
        String nuevoCodigo = txtCodigo.getText();
        boolean codigoExiste = listaProductos.stream()
                .anyMatch(p -> p.getCodigo().equals(nuevoCodigo));

        if (codigoExiste) {
            mostrarAlerta("Error", "Ya existe un producto con ese código", Alert.AlertType.ERROR);
            return;
        }

        try {
            Producto producto = new Producto();
            producto.setCodigo(txtCodigo.getText());
            producto.setNombre(txtNombre.getText());
            producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
            producto.setPvp(Double.parseDouble(txtPvp.getText()));
            producto.setStockInicial(Integer.parseInt(txtStock.getText()));
            producto.setStockActual(Integer.parseInt(txtStock.getText()));
            producto.setIva(Double.parseDouble(txtIva.getText()));

            Categoria categoriaSeleccionada = comboCategoria.getSelectionModel().getSelectedItem();
            producto.setIdCategoria(categoriaSeleccionada.getId());
            producto.setCategoria(categoriaSeleccionada);
            producto.setEstado("A");

            listaProductos.add(producto);
            limpiarCampos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al crear el producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void guardarProductos(ActionEvent actionEvent) {
        if (listaProductos.isEmpty()) {
            mostrarAlerta("Advertencia", "No hay productos para guardar", Alert.AlertType.WARNING);
            return;
        }

        boolean todosGuardados = true;
        String mensajeError = "";

        for (Producto producto : listaProductos) {
            try {
                if (!productoDAO.guardar(producto)) {
                    todosGuardados = false;
                    mensajeError += "Error al guardar el producto: " + producto.getCodigo() + "\n";
                }
            } catch (Exception e) {
                todosGuardados = false;
                mensajeError += "Error al guardar el producto " + producto.getCodigo() + ": " + e.getMessage() + "\n";
            }
        }

        if (todosGuardados) {
            mostrarAlerta("Éxito", "Todos los productos fueron guardados correctamente", Alert.AlertType.INFORMATION);
            listaProductos.clear();
        } else {
            mostrarAlerta("Error", "Algunos productos no se pudieron guardar:\n" + mensajeError, Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void cancelarRegistro(ActionEvent actionEvent) {
        if (!listaProductos.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar cancelación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro que desea cancelar? Se perderán todos los productos no guardados.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                limpiarCampos();
                listaProductos.clear();
            }
        } else {
            limpiarCampos();
        }
    }

    private boolean confirmarEliminacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea eliminar este producto de la lista?");
        return alert.showAndWait().get() == ButtonType.OK;
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
            Double precio = Double.parseDouble(txtPrecio.getText());
            Double pvp = Double.parseDouble(txtPvp.getText());
            Integer stock = Integer.parseInt(txtStock.getText());
            Double iva = Double.parseDouble(txtIva.getText());

            if (precio < 0 || pvp < 0 || stock < 0 || iva < 0) {
                mostrarAlerta("Error", "Los valores numéricos no pueden ser negativos", Alert.AlertType.ERROR);
                return false;
            }

            if (iva > 1) {
                mostrarAlerta("Error", "El IVA debe ser un valor entre 0 y 1", Alert.AlertType.ERROR);
                return false;
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Formato de número inválido en uno de los campos", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtPvp.clear();
        txtStock.clear();
        txtIva.clear();
        comboCategoria.getSelectionModel().clearSelection();
        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}