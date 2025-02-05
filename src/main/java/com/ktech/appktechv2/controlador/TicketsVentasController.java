package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import com.ktech.appktechv2.modelo.*;
import com.ktech.appktechv2.util.EmailSender;
import com.ktech.appktechv2.util.InventarioManager;
import com.ktech.appktechv2.util.TicketPDFGenerator;
import com.ktech.appktechv2.util.ValidadorDocumento;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class TicketsVentasController implements Initializable {

    @FXML
    private ComboBox<String> cmb_establecimiento;
    @FXML
    private TextField txt_nombre_comercial;
    @FXML
    private DatePicker dtp_fecha;
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
    private TextField auto_field_producto_list;
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
    private Button btn_cancelar;
    @FXML
    private Button btn_guardar_enviar;
    private ContextMenu autoCompletePopup;
    private ProductoDAO productoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmb_tipo_ident.getItems().addAll("CEDULA", "RUC", "PASAPORTE", "VENTA A CONSUMIDOR FINAL", "IDENTIFICACION DEL EXTERIOR");
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

    @FXML
    private void acc_buscar_ident(ActionEvent event) {
        // Obtener el número de identificación ingresado
        String identificacion = txt_identificacion.getText().trim();

        // Validar que el campo no esté vacío
        if (ValidadorDocumento.esCampoVacio(identificacion)) {
            mostrarAlerta("Error", "Ingrese un número de identificación.", Alert.AlertType.ERROR);
            return;
        }

        // Determinar el tipo de identificación basado en la longitud o valor específico
        String tipoIdentificacion;
        boolean esValido = false;

        if (identificacion.equals("9999999999999")) {
            tipoIdentificacion = "VENTA A CONSUMIDOR FINAL";
            esValido = true; // Este caso siempre es válido
        } else if (identificacion.length() == 10) {
            tipoIdentificacion = "CEDULA";
            esValido = ValidadorDocumento.validarCedula(identificacion);
        } else if (identificacion.length() == 13) {
            tipoIdentificacion = "RUC";
            esValido = ValidadorDocumento.validarRUC(identificacion);
        } else {
            tipoIdentificacion = "PASAPORTE"; // Para cualquier otro caso, asumimos pasaporte
            esValido = true; // No se valida el pasaporte
        }

        // Validar el número de identificación
        if (!esValido) {
            mostrarAlerta("Error", "El número de identificación no es válido.", Alert.AlertType.ERROR);
            return;
        }

        // Actualizar el ComboBox de Tipo de Identificación
        cmb_tipo_ident.getSelectionModel().select(tipoIdentificacion);

        // Manejar el caso "VENTA A CONSUMIDOR FINAL"
        if (tipoIdentificacion.equals("VENTA A CONSUMIDOR FINAL")) {
            txt_razon_social.setText("CONSUMIDOR FINAL");
            txt_direccion.setText("DIRECCIÓN NO ESPECIFICADA");
            txt_telefono.clear();
            txt_correo.clear();
            return;
        }

        // Buscar el cliente en la base de datos
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorCedulaONombre(identificacion);

        if (cliente != null) {
            // Rellenar los campos del adquirente
            txt_razon_social.setText(cliente.getNombre() + " " + cliente.getApellido());
            txt_direccion.setText(cliente.getDireccion());
            txt_telefono.setText(cliente.getTelefono());
            txt_correo.setText(cliente.getEmail());
        } else {
            // Mostrar ventana emergente para registrar al cliente
            mostrarAlerta("Advertencia", "Cliente no encontrado. ¿Desea registrar un nuevo cliente?", Alert.AlertType.WARNING);

            try {
                // Cargar la vista de registro de clientes
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/Clientes.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la ventana emergente
                ClientesController clientesController = loader.getController();

                // Crear una nueva escena y mostrarla como ventana emergente
                Stage stage = new Stage();
                stage.setTitle("Registrar Nuevo Cliente");
                stage.setScene(new Scene(root));
                stage.setResizable(false);

                // Hacer que la ventana sea modal
                stage.initOwner(txt_identificacion.getScene().getWindow());

                // Mostrar la ventana y esperar a que se cierre
                stage.showAndWait();

                // Verificar si se registró un nuevo cliente
                Cliente nuevoCliente = clientesController.obtenerClienteSeleccionado();
                if (nuevoCliente != null) {
                    // Rellenar los campos con los datos del nuevo cliente
                    txt_razon_social.setText(nuevoCliente.getNombre() + " " + nuevoCliente.getApellido());
                    txt_direccion.setText(nuevoCliente.getDireccion());
                    txt_telefono.setText(nuevoCliente.getTelefono());
                    txt_correo.setText(nuevoCliente.getEmail());
                }
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Ocurrió un error al abrir la ventana de registro de clientes.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void acc_buscar_producto(ActionEvent event) {
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

    @FXML
    private void acc_cancelar(ActionEvent event) {
        // Mostrar mensaje de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea cancelar? Todos los datos ingresados se perderán.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                limpiarCampos();
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

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        // Limpiar campos de identificación y cliente
        txt_identificacion.clear();
        cmb_tipo_ident.getSelectionModel().clearSelection();
        txt_razon_social.clear();
        txt_direccion.clear();
        txt_telefono.clear();
        txt_correo.clear();

        // Limpiar campos de emisor
        cmb_establecimiento.getSelectionModel().clearSelection();
        txt_nombre_comercial.clear();

        // Limpiar campos de productos
        tbl_detalle.getItems().clear();

        // Limpiar campos de formas de pago
        tbl_forma_pago.getItems().clear();

        // Limpiar campos de totales
        lbl_subtotal_15.setText("0.00");
        lbl_subtotal_0.setText("0.00");
        lbl_iva_15.setText("0.00");
        lbl_total_pagar.setText("0.00");

        // Limpiar otros campos
        dtp_fecha.setValue(LocalDate.now()); // Restablecer la fecha actual
    }

    private Emisor obtenerEmisorSeleccionado() {
        EmisorDAO emisorDAO = new EmisorDAO();
        String codigoEstablecimiento = cmb_establecimiento.getSelectionModel().getSelectedItem().split(" - ")[0];
        return emisorDAO.obtenerEmisorPorCodigoEstablecimiento(codigoEstablecimiento);
    }

    @FXML
    private void acc_guardar_enviar(ActionEvent event) {
        try {
            // Obtener datos necesarios
            int idEmisor = obtenerIdEmisorSeleccionado();
            String numeroSecuencial = generarNumeroSecuencial(idEmisor);
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorCedulaONombre(txt_identificacion.getText());
            if (cliente == null) {
                mostrarAlerta("Error", "Cliente no encontrado.", Alert.AlertType.ERROR);
                return;
            }
            Emisor emisor = obtenerEmisorSeleccionado();
            if (emisor == null) {
                mostrarAlerta("Error", "No se pudo obtener el emisor.", Alert.AlertType.ERROR);
                return;
            }

            // Crear objeto Venta
            Venta venta = new Venta();
            venta.setIdCliente(cliente.getIdCliente());  // Asignar el ID del cliente encontrado
            venta.setIdEmisor(idEmisor);
            venta.setFecha(LocalDateTime.now());
            venta.setFechaEmision(dtp_fecha.getValue().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
            venta.setTotal(Double.parseDouble(lbl_total_pagar.getText()));
            venta.setEstado("Emitida");
            venta.setNumeroSecuencial(numeroSecuencial);
            venta.setRucComprador(txt_identificacion.getText());
            venta.setRazonSocialComprador(txt_razon_social.getText());
            venta.setDireccionComprador(txt_direccion.getText());

            // Detalles de la venta
            List<DetalleVenta> detalles = new ArrayList<>();
            for (Producto producto : tbl_detalle.getItems()) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(producto.getId());
                detalle.setCantidad(producto.getCantidad());
                detalle.setPrecioUnitario(producto.getPvp());
                detalle.setSubtotal(producto.getSubtotal());
                detalle.setIva(producto.getIva());
                detalle.setTotal(producto.getTotal());
                detalles.add(detalle);
            }
            venta.setDetalles(detalles);

            // Registrar la venta
            VentaDAO ventaDAO = new VentaDAO();
            if (ventaDAO.registrarVenta(venta)) {
                // Actualizar el stock usando InventarioManager
                InventarioManager inventarioManager = new InventarioManager();
                boolean stockActualizado = true;

                for (DetalleVenta detalle : detalles) {
                    int idProducto = detalle.getIdProducto();
                    int cantidadVendida = detalle.getCantidad();

                    // Verificar si hay suficiente stock
                    if (!inventarioManager.verificarStock(idProducto, cantidadVendida)) {
                        mostrarAlerta("Error", "No hay suficiente stock para el producto con ID: " + idProducto, Alert.AlertType.ERROR);
                        stockActualizado = false;
                        break; // Detener el proceso si no hay suficiente stock
                    }

                    // Actualizar el stock
                    if (!inventarioManager.actualizarStock(idProducto, cantidadVendida)) {
                        mostrarAlerta("Error", "No se pudo actualizar el stock para el producto con ID: " + idProducto, Alert.AlertType.ERROR);
                        stockActualizado = false;
                        break;
                    }
                }

                if (stockActualizado) {
                    // Generar el PDF
                    venta.setMetodoPago(obtenerMetodoPagoSeleccionado()); // Implementa este método según tu lógica
                    String pdfPath = "src/main/resources/tickets/ticket_" + numeroSecuencial + ".pdf";
                    String generatedPdf = TicketPDFGenerator.generateTicket(emisor, venta, tbl_detalle.getItems(), pdfPath);

                    if (generatedPdf != null) {
                        mostrarAlerta("Éxito", "Venta registrada correctamente. Ticket generado en: " + pdfPath, Alert.AlertType.INFORMATION);

                        // Enviar el correo con el PDF adjunto
                        String correoCliente = cliente.getEmail(); // Suponiendo que el cliente tiene un campo "email"
                        if (correoCliente != null && !correoCliente.isEmpty()) {
                            // Configuración del servidor SMTP (ejemplo para Gmail)
                            String host = "smtp.gmail.com";
                            String port = "587";
                            ConfiguracionEmailDAO configDAO = new ConfiguracionEmailDAO();
                            ConfiguracionEmail config = configDAO.obtenerConfiguracionActual();
                            String username = config.getCorreoElectronico();
                            String appPassword = config.getPasswordApp();

                            // Crear instancia de EmailSender
                            EmailSender emailSender = new EmailSender(host, port, username, appPassword);

                            // Datos del correo
                            String toAddress = correoCliente; // Usar el correo del cliente
                            String subject = "Ticket de Compra - " + numeroSecuencial;
                            String message = "Estimado/a " + cliente.getNombre() + ",\n\nAdjuntamos su ticket de compra. Gracias por preferirnos.";
                            String attachmentPath = pdfPath;

                            // Enviar el correo
                            emailSender.sendEmailWithAttachment(toAddress, subject, message, attachmentPath);
                            mostrarAlerta("Éxito", "Correo enviado exitosamente al cliente.", Alert.AlertType.INFORMATION);
                        } else {
                            mostrarAlerta("Advertencia", "El cliente no tiene un correo registrado.", Alert.AlertType.WARNING);
                        }
                    } else {
                        mostrarAlerta("Error", "No se pudo generar el ticket en PDF.", Alert.AlertType.ERROR);
                    }

                    limpiarCampos();
                } else {
                    // Revertir la venta si no se pudo actualizar el stock
                    ventaDAO.eliminarVenta(venta.getIdVenta());
                    mostrarAlerta("Error", "No se pudo completar la venta debido a problemas de stock.", Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error", "No se pudo registrar la venta.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al procesar la venta.", Alert.AlertType.ERROR);
        }
    }

    private int obtenerIdEmisorSeleccionado() {
        EmisorDAO emisorDAO = new EmisorDAO();
        String codigoEstablecimiento = cmb_establecimiento.getSelectionModel().getSelectedItem().split(" - ")[0];
        Emisor emisor = emisorDAO.obtenerEmisorPorCodigoEstablecimiento(codigoEstablecimiento);
        return emisor != null ? emisor.getIdEmisor() : 0;
    }

    private String generarNumeroSecuencial(int idEmisor) throws SQLException {
        String numeroSecuencial = "";
        String sql = "{ call ObtenerSiguienteSecuencialVenta(?, ?) }";

        try (Connection con = new SqlConnection().getConexion(); CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idEmisor);
            cs.registerOutParameter(2, Types.CHAR);
            cs.execute();
            numeroSecuencial = cs.getString(2);
        }

        return numeroSecuencial;
    }

    private String obtenerMetodoPagoSeleccionado() {
        // Obtener los métodos de pago de la tabla
        List<FormaPago> formasPago = tbl_forma_pago.getItems();

        // Validar que haya al menos un método de pago seleccionado
        if (formasPago == null || formasPago.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar al menos un método de pago.", Alert.AlertType.ERROR);
            return null;
        }

        // Concatenar todos los métodos de pago en una sola cadena
        StringBuilder metodoPago = new StringBuilder();
        for (FormaPago formaPago : formasPago) {
            metodoPago.append(formaPago.getFormaPago()).append("\n");
        }

        // Retornar la cadena con los métodos de pago
        return metodoPago.toString().trim(); // Eliminar el último salto de línea
    }

}
