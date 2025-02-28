package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import com.ktech.appktechv2.modelo.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TablaProductosController implements Initializable {
    @FXML
    private TableColumn columnaId;
    @FXML
    private TableColumn columnaCodigo;
    @FXML
    private TableColumn columnaNombre;
    @FXML
    private TableColumn columnaPrecio;
    @FXML
    private TableColumn columnaPvp;
    @FXML
    private TableColumn columnaStock;
    @FXML
    private TableColumn columnaIva;
    @FXML
    private TableColumn columnaEstado;
    @FXML
    private TableColumn columnaCategoria;
    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private Button btnHabilitar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;

    private ObservableList<Producto> listaProductos;
    private StackPane contentArea;
    private MainLayoutController mainLayoutController;

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }

    public void setContentArea(StackPane contentArea) {
        this.contentArea = contentArea;
    }

    private Gestion_ProductosController gestionProductosController;

    public void setGestionProductosController(Gestion_ProductosController controller) {
        this.gestionProductosController = controller;
    }

    @FXML
    private void abrirActualizacionProducto(ActionEvent actionEvent) {
        Producto productoSeleccionado = getProductoSeleccionado();
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un producto para actualizar", Alert.AlertType.WARNING);
            return;
        }

        if (gestionProductosController != null) {
            gestionProductosController.cargarVistaActualizarProducto(productoSeleccionado);
        } else {
            mostrarAlerta("Error", "Error de inicialización", Alert.AlertType.ERROR);
        }
    }

    public void recargarDatos() {
        cargarProductos();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnas();
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        cargarProductos();
        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && "I".equals(newSelection.getEstado())) {
                btnHabilitar.setVisible(true);
            } else {
                btnHabilitar.setVisible(false);
            }
        });
        campoBusqueda.setOnAction(event -> buscarProducto());
    }

    public Producto getProductoSeleccionado() {
        return tablaProductos.getSelectionModel().getSelectedItem();
    }


    private void configurarColumnas() {
        columnaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("Codigo"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        columnaPvp.setCellValueFactory(new PropertyValueFactory<>("Pvp"));
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("StockActual"));
        columnaIva.setCellValueFactory(new PropertyValueFactory<>("Iva"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
        columnaCategoria.setCellValueFactory(new PropertyValueFactory<>("NombreCategoria"));
    }

    private void cargarProductos() {
        listaProductos = FXCollections.observableArrayList();
        String query = """
                SELECT p.*, c.Nombre as NombreCategoria
                FROM Productos p
                LEFT JOIN Categorias c ON p.IDCategoria = c.IDCategoria
                WHERE p.Estado = 'A' OR p.Estado = 'I'
                """;
        try (Connection conn = new SqlConnection().getConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                listaProductos.add(new Producto(
                        rs.getInt("IDProducto"),
                        rs.getString("Codigo"),
                        rs.getString("Nombre"),
                        rs.getDouble("Precio"),
                        rs.getDouble("PVP"),
                        rs.getInt("StockActual"),
                        rs.getDouble("IVA"),
                        rs.getString("Estado"),
                        rs.getString("NombreCategoria")
                ));
            }
            tablaProductos.setItems(listaProductos);
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al cargar productos: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarProducto() {
        String filtro = campoBusqueda.getText().toLowerCase();
        ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();
        for (Producto producto : listaProductos) {
            if (producto.getCodigo().toLowerCase().contains(filtro) || producto.getNombre().toLowerCase().contains(filtro)) {
                productosFiltrados.add(producto);
            }
        }
        if (productosFiltrados.isEmpty()) {
            mostrarAlerta("Información", "No se encontraron productos con el criterio de búsqueda.", Alert.AlertType.INFORMATION);
        } else {
            tablaProductos.setItems(productosFiltrados);
        }
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Por favor, seleccione un producto para eliminar", Alert.AlertType.WARNING);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de que desea eliminar este producto?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");
        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            eliminarProductoLogico(productoSeleccionado.getId());
        }
    }

    private void eliminarProductoLogico(int idProducto) {
        String query = "UPDATE Productos SET Estado = 'I' WHERE IDProducto = ?";
        try (Connection conn = new SqlConnection().getConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            int resultado = stmt.executeUpdate();
            if (resultado > 0) {
                mostrarAlerta("Éxito", "Producto eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarProductos(); // Recargar la tabla
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar el producto: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void habilitarProducto(ActionEvent event) {
        Producto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Debe seleccionar un producto antes de habilitar.", Alert.AlertType.WARNING);
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar habilitación");
        confirmacion.setHeaderText("¿Está seguro de que desea habilitar este producto?");
        confirmacion.setContentText("Esta acción habilitará el producto seleccionado.");
        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            habilitarProductoLogico(productoSeleccionado.getId());
        }
    }

    private void habilitarProductoLogico(int idProducto) {
        String query = "UPDATE Productos SET Estado = 'A' WHERE IDProducto = ?";
        try (Connection conn = new SqlConnection().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idProducto);
            pstmt.executeUpdate();
            cargarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}