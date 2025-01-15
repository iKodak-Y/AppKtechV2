package com.ktech.appktechv2.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import com.ktech.appktechv2.modelo.Producto;
import com.ktech.appktechv2.modelo.ProductoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Side;

public class FacturaController implements Initializable {

    @FXML
    private ComboBox<?> cmb_establecimiento;
    @FXML
    private TextField txt_nombre_comercial;
    @FXML
    private DatePicker dtp_fecha;
    @FXML
    private ComboBox<?> cmb_punto_emision;
    @FXML
    private TextField txt_guia_remision;
    @FXML
    private TextField txt_identificacion;
    @FXML
    private ImageView btn_buscar_ident;
    @FXML
    private ComboBox<?> cmb_tipo_ident;
    @FXML
    private TextField txt_razon_social;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_correo;
    @FXML
    private Button btn_buscar_producto;
    @FXML
    private TableView<?> tbl_detalle;
    @FXML
    private TableColumn<?, ?> col_codigo;
    @FXML
    private TableColumn<?, ?> col_cantidad;
    @FXML
    private TableColumn<?, ?> col_descripcion;
    @FXML
    private TableColumn<?, ?> col_precio_unitario;
    @FXML
    private TableColumn<?, ?> col_tarifa;
    @FXML
    private TableColumn<?, ?> col_descuento;
    @FXML
    private TableColumn<?, ?> col_valor_total;
    @FXML
    private TableColumn<?, ?> col_acciones;
    @FXML
    private Label lbl_subtotal_15;
    @FXML
    private Label lbl_subtotal_0;
    @FXML
    private Label lbl_iva_15;
    @FXML
    private Label lbl_total_pagar;
    @FXML
    private TableView<?> tbl_forma_pago;
    @FXML
    private TableColumn<?, ?> col_forma_pago;
    @FXML
    private TableColumn<?, ?> col_valor;
    @FXML
    private TableColumn<?, ?> col_acciones_forma_pago;
    @FXML
    private Button btn_efectivo;
    @FXML
    private Button btn_tarjeta_debito;
    @FXML
    private Button btn_tarjeta_credito;
    @FXML
    private Button btn_firmar_enviar;
    @FXML
    private Button btn_guardar_sin_firmar;
    @FXML
    private Button btn_cerrar;

    @FXML
    private TextField auto_field_producto_list;
    private ContextMenu autoCompletePopup;
    private ProductoDAO productoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa el ContextMenu para el autocompletado
        autoCompletePopup = new ContextMenu();
        // Inicializa el DAO de productos
        productoDAO = new ProductoDAO();

        // Añade un listener al campo de texto para manejar el autocompletado
        auto_field_producto_list.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                autoCompletePopup.hide();
            } else {
                List<Producto> searchResult = productoDAO.buscarPorCodigoONombre(newValue);
                if (!searchResult.isEmpty()) {
                    populatePopup(searchResult);
                    if (!autoCompletePopup.isShowing()) {
                        autoCompletePopup.show(auto_field_producto_list, Side.BOTTOM, 0, 0);
                    }
                } else {
                    autoCompletePopup.hide();
                }
            }
        });

        // Oculta el popup cuando el campo pierde el foco
        auto_field_producto_list.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // si pierde el foco
                autoCompletePopup.hide();
            }
        });
    }

    @FXML
    private void acc_buscar_ident(MouseEvent event) {
    }

    @FXML
    private void acc_buscar_producto(ActionEvent event) {
    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
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
                auto_field_producto_list.setText(producto.toString());
                autoCompletePopup.hide();
                // Aquí puedes añadir lógica adicional cuando un producto es seleccionado
            });

            menuItems.add(item);
        }

        autoCompletePopup.getItems().clear();
        autoCompletePopup.getItems().addAll(menuItems);
    }
}
