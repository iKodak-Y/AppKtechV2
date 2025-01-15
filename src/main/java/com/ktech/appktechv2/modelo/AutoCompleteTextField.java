package com.ktech.appktechv2.modelo;

import com.ktech.appktechv2.SqlConnection;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.geometry.Side;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class AutoCompleteTextField extends TextField {

    private ContextMenu entriesPopup;
    private SqlConnection sqlConnection;
    private ProductoCallback callback;

    // Clase interna para manejar los datos del producto
    public static class Producto {

        private String codigo;
        private String nombre;
        // Puedes agregar más campos según necesites, por ejemplo:
        // private double precio;
        // private int stock;

        public Producto(String codigo, String nombre) {
            this.codigo = codigo;
            this.nombre = nombre;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return codigo + " - " + nombre;
        }
    }

    // Interface para el callback
    public interface ProductoCallback {

        void onProductoSeleccionado(Producto producto);
    }

    public AutoCompleteTextField() {
        super();
        this.sqlConnection = new SqlConnection();
        this.entriesPopup = new ContextMenu();

        // Listener para cambios en el texto
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                entriesPopup.hide();
            } else {
                List<Producto> searchResult = buscarProductos(newValue);
                if (!searchResult.isEmpty()) {
                    populatePopup(searchResult);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });

        // Ocultar popup cuando el campo pierde el foco
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // si pierde el foco
                entriesPopup.hide();
            }
        });
    }

    // Método para buscar productos en la base de datos
    private List<Producto> buscarProductos(String searchText) {
        List<Producto> resultList = new LinkedList<>();
        String busqueda = "%" + searchText.toLowerCase() + "%";

        // MODIFICAR: Ajusta esta consulta según tu estructura de base de datos
        String sql = "SELECT codigo, nombre FROM productos WHERE "
                + "LOWER(codigo) LIKE ? OR LOWER(nombre) LIKE ? "
                + "ORDER BY nombre LIMIT 10"; // Limita a 10 resultados para mejor rendimiento

        try (Connection conn = sqlConnection.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, busqueda);
            pstmt.setString(2, busqueda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Producto producto = new Producto(
                            rs.getString("codigo"),
                            rs.getString("nombre")
                    // Agregar más campos según necesites
                    );
                    resultList.add(producto);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al buscar productos: " + e.getMessage());
        }

        return resultList;
    }

    private void populatePopup(List<Producto> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();

        // Limitar a 10 resultados para mejor rendimiento
        int maxEntries = Math.min(searchResult.size(), 10);

        for (int i = 0; i < maxEntries; i++) {
            final Producto producto = searchResult.get(i);
            Label entryLabel = new Label(producto.toString());
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);

            item.setOnAction(event -> {
                setText(producto.toString());
                entriesPopup.hide();
                if (callback != null) {
                    callback.onProductoSeleccionado(producto);
                }
            });

            menuItems.add(item);
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    // Método para establecer el callback
    public void setProductoCallback(ProductoCallback callback) {
        this.callback = callback;
    }
}
