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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

}
