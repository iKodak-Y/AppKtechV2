package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class FacturaController implements Initializable {

    @FXML
    private ComboBox<String> cmb_establecimiento;
    @FXML
    private TextField txt_nombre_comercial;
    @FXML
    private DatePicker dtp_fecha;
    @FXML
    private ComboBox<String> cmb_punto_emision;
    @FXML
    private TextField txt_guia_remision;
    @FXML
    private TextField txt_identificacion;
    @FXML
    private Button btn_buscar_ident;
    @FXML
    private ComboBox<String> cmb_tipo_ident;
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
    private TableView<Producto> tbl_detalle;
    @FXML
    private TableColumn<Producto, String> col_codigo;
    @FXML
    private TableColumn<Producto, Integer> col_cantidad;
    @FXML
    private TableColumn<Producto, String> col_descripcion;
    @FXML
    private TableColumn<Producto, Double> col_precio_unitario;
    @FXML
    private TableColumn<Producto, Double> col_tarifa;
    @FXML
    private TableColumn<Producto, Double> col_descuento;
    @FXML
    private TableColumn<Producto, Double> col_valor_total;
    @FXML
    private TableColumn<Producto, Void> col_acciones;
    @FXML
    private Label lbl_subtotal_15;
    @FXML
    private Label lbl_subtotal_0;
    @FXML
    private Label lbl_iva_15;
    @FXML
    private Label lbl_total_pagar;
    @FXML
    private TableView<FormaPago> tbl_forma_pago;
    @FXML
    private TableColumn<FormaPago, String> col_forma_pago;
    @FXML
    private TableColumn<FormaPago, Double> col_valor;
    @FXML
    private TableColumn<FormaPago, Void> col_acciones_forma_pago;
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
    private TextField auto_field_producto_list;
    private ContextMenu autoCompletePopup;
    private ProductoDAO productoDAO;
    @FXML
    private Button btn_cancelar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // hacer la tabla editable
        tbl_detalle.setEditable(true);

        // Inicializar el ContextMenu para el autocompletado
        autoCompletePopup = new ContextMenu();
        // Inicializar el DAO de productos
        productoDAO = new ProductoDAO();

        // Autocompletar la fecha de emisión con la fecha actual
        dtp_fecha.setValue(LocalDate.now());

        // Cargar datos en ComboBox de establecimientos y puntos de emisión
        cargarDatosEstablecimientos();
        cargarDatosPuntoEmision();

        // Añadir listener al ComboBox de establecimientos
        cmb_establecimiento.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EmisorDAO emisorDAO = new EmisorDAO();
                Emisor emisor = emisorDAO.obtenerEmisorPorCodigoEstablecimiento(newValue.split(" - ")[0]);
                if (emisor != null) {
                    txt_nombre_comercial.setText(emisor.getNombreComercial());
                    txt_nombre_comercial.setEditable(false);
                }
            }
        });

        // Añadir listener al campo de texto para manejar el autocompletado
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

        // Ocultar el popup cuando el campo pierde el foco
        auto_field_producto_list.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) { // si pierde el foco
                autoCompletePopup.hide();
            }
        });

        // Configurar las columnas de la tabla de detalles de productos
        col_codigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        // Columna cantidad (editable)
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_cantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_cantidad.setOnEditCommit(event -> {
            Producto producto = event.getRowValue();
            if (event.getNewValue() > 0) {
                producto.setCantidad(event.getNewValue());
                calcularTotales();
                tbl_detalle.refresh();
            }
        });
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Columna precio unitario (editable)
        col_precio_unitario.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        col_precio_unitario.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public String toString(Double value) {
                return String.format("%.2f", value);
            }
        }));
        col_precio_unitario.setOnEditCommit(event -> {
            Producto producto = event.getRowValue();
            if (event.getNewValue() > 0) {
                producto.setPvp(event.getNewValue());
                calcularTotales();
                tbl_detalle.refresh();
            }
        });

        // Columna tarifa (no editable)
        col_tarifa.setCellValueFactory(new PropertyValueFactory<>("iva"));
        col_tarifa.setEditable(false);

        // Columna valor total (muestra el subtotal sin IVA)
        col_valor_total.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        col_valor_total.setCellFactory(column -> new TableCell<Producto, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item));
                }
            }
        });

        // Configurar botones de acción para eliminar productos
        col_acciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnEliminar.setOnAction(event -> {
                        Producto producto = getTableView().getItems().get(getIndex());
                        tbl_detalle.getItems().remove(producto);
                        calcularTotales();
                    });
                    setGraphic(btnEliminar);
                }
            }
        });

        // Configurar columnas y botones de acción en la tabla de forma de pago
        col_forma_pago.setCellValueFactory(new PropertyValueFactory<>("formaPago"));
        col_valor.setCellValueFactory(new PropertyValueFactory<>("valorPago"));
        col_valor.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter() {
            @Override
            public String toString(Double value) {
                return String.format("%.2f", value);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));
        col_valor.setOnEditCommit(event -> {
            FormaPago formaPago = event.getRowValue();
            formaPago.setValorPago(event.getNewValue());
            calcularTotales();
        });
        col_acciones_forma_pago.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btnEliminar.setOnAction(event -> {
                        FormaPago formaPago = getTableView().getItems().get(getIndex());
                        tbl_forma_pago.getItems().remove(formaPago);
                        calcularTotales();
                    });
                    setGraphic(btnEliminar);
                }
            }
        });
    }

    private void cargarDatosEstablecimientos() {
        EmisorDAO emisorDAO = new EmisorDAO();
        List<Emisor> emisores = emisorDAO.obtenerTodosLosEmisores();
        for (Emisor emisor : emisores) {
            cmb_establecimiento.getItems().add(emisor.getCodigoEstablecimiento() + " - " + emisor.getDireccion());
        }
    }

    private void cargarDatosPuntoEmision() {
        EmisorDAO emisorDAO = new EmisorDAO();
        Emisor emisor = emisorDAO.obtenerEmisorPorDefecto();
        if (emisor != null) {
            cmb_punto_emision.getItems().add(emisor.getPuntoEmision());
        }
    }

    @FXML
    private void acc_buscar_producto(ActionEvent event) {
        // Implementar búsqueda de producto y añadirlo a la tabla
    }

    @FXML
    private void populatePopup(List<Producto> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = Math.min(searchResult.size(), 10);

        for (int i = 0; i < maxEntries; i++) {
            final Producto producto = searchResult.get(i);
            Label entryLabel = new Label(producto.toString());
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);

            item.setOnAction(event -> {
                auto_field_producto_list.setText("");
                producto.setCantidad(1);
                producto.calcularTotal();
                tbl_detalle.getItems().add(producto);
                autoCompletePopup.hide();
                calcularTotales();
            });

            menuItems.add(item);
        }

        autoCompletePopup.getItems().clear();
        autoCompletePopup.getItems().addAll(menuItems);
    }

    @FXML
    private void acc_firmar_enviar(ActionEvent event) {
        // Implementar generación de clave de acceso y
        // la lógica de firma y envío de la factura
    }

    @FXML
    private void acc_guardar_sin_firmar(ActionEvent event) {
        // Implementar lógica para guardar la factura sin firmar
    }

    @FXML
    private void acc_cancelar(ActionEvent event) {
        // Implementar lógica para cancelar la operación
    }

    @FXML
    private void acc_metodo_pago_efectivo(ActionEvent event) {
        double totalConIVA = obtenerTotalConIVA();
        FormaPago formaPago = new FormaPago("01 - SIN UTILIZACIÓN DEL SISTEMA FINANCIERO", totalConIVA);
        tbl_forma_pago.getItems().add(formaPago);
    }

    @FXML
    private void acc_metodo_pago_tarjeta_debito(ActionEvent event) {
        double totalConIVA = obtenerTotalConIVA();
        FormaPago formaPago = new FormaPago("16 - TARJETA DE DÉBITO", totalConIVA);
        tbl_forma_pago.getItems().add(formaPago);
    }

    @FXML
    private void acc_metodo_pago_tarjeta_credito(ActionEvent event) {
        double totalConIVA = obtenerTotalConIVA();
        FormaPago formaPago = new FormaPago("19 - TARJETA DE CRÉDITO", totalConIVA);
        tbl_forma_pago.getItems().add(formaPago);
    }

    private double obtenerTotalConIVA() {
        // Obtener el valor de lbl_total_pagar y convertirlo a double
        return Double.parseDouble(lbl_total_pagar.getText());
    }

    private double calcularTotalFactura() {
        return tbl_detalle.getItems().stream()
                .mapToDouble(Producto::getSubtotal)
                .sum();
    }

    private double calcularTotalConIVA() {
        return tbl_detalle.getItems().stream()
                .mapToDouble(Producto::getTotal)
                .sum();
    }

    private void calcularTotales() {
        double subtotal15 = 0;
        double subtotal0 = 0;
        double iva15 = 0;

        for (Producto producto : tbl_detalle.getItems()) {
            if (producto.getIva() > 0) {
                subtotal15 += producto.getSubtotal();
                iva15 += producto.getTotal() - producto.getSubtotal();
            } else {
                subtotal0 += producto.getSubtotal();
            }
        }

        double totalPagar = subtotal15 + subtotal0 + iva15;

        lbl_subtotal_15.setText(String.format("%.2f", subtotal15));
        lbl_subtotal_0.setText(String.format("%.2f", subtotal0));
        lbl_iva_15.setText(String.format("%.2f", iva15));
        lbl_total_pagar.setText(String.format("%.2f", totalPagar));
    }

    @FXML
    private void acc_buscar_ident(ActionEvent event) {
        System.out.println("ENTRANDO A CLIENTES");
        String identificacion = txt_identificacion.getText();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCedulaONombre(identificacion);
        if (cliente != null) {
            txt_razon_social.setText(cliente.getNombre() + " " + cliente.getApellido());
            txt_direccion.setText(cliente.getDireccion());
            txt_telefono.setText(cliente.getTelefono());
            txt_correo.setText(cliente.getEmail());
        } else {
            // Manejar caso donde no se encuentra el cliente
            System.out.println("Cliente no encontrado");
        }
    }
}
