package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Registro_EmisorController implements Initializable {

    @FXML
    private TextField txtRuc;
    @FXML
    private TextField txtRazonSocial;
    @FXML
    private TextField txtNombreComercial;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtCodigoEstablecimiento;
    @FXML
    private TextField txtPuntoEmision;
    @FXML
    private ComboBox<String> cmbTipoAmbiente;
    @FXML
    private TextField txtCertificadoPath;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private CheckBox chkObligadoContabilidad;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnConfigSri;
    @FXML
    private Button btnCancelar;

    private final SqlConnection sqlConnection = new SqlConnection();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ComboBox de tipo ambiente
        cmbTipoAmbiente.setItems(FXCollections.observableArrayList(
                "1 - Pruebas",
                "2 - Producción"
        ));

        // Validaciones
        txtRuc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRuc.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (txtRuc.getText().length() > 13) {
                txtRuc.setText(oldValue);
            }
        });

        txtCodigoEstablecimiento.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCodigoEstablecimiento.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (txtCodigoEstablecimiento.getText().length() > 3) {
                txtCodigoEstablecimiento.setText(oldValue);
            }
        });

        txtPuntoEmision.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPuntoEmision.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (txtPuntoEmision.getText().length() > 3) {
                txtPuntoEmision.setText(oldValue);
            }
        });
    }

    @FXML
    private void guardarEmisor() {
        if (!validarCampos()) {
            return;
        }

        try (Connection conn = sqlConnection.getConexion()) {
            String sql = "INSERT INTO Emisor (ruc, razon_social, nombre_comercial, direccion, "
                    + "codigo_establecimiento, punto_emision, tipo_ambiente, obligado_contabilidad, "
                    + "certificado_path, contrasena_certificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtRuc.getText());
            stmt.setString(2, txtRazonSocial.getText());
            stmt.setString(3, txtNombreComercial.getText());
            stmt.setString(4, txtDireccion.getText());
            stmt.setString(5, txtCodigoEstablecimiento.getText());
            stmt.setString(6, txtPuntoEmision.getText());
            stmt.setString(7, cmbTipoAmbiente.getValue().substring(0, 1));
            stmt.setBoolean(8, chkObligadoContabilidad.isSelected());
            stmt.setString(9, txtCertificadoPath.getText());
            stmt.setString(10, txtContrasena.getText());

            stmt.executeUpdate();

            mostrarAlerta("Éxito", "Emisor registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();

        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al guardar el emisor: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void seleccionarCertificado() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Certificado Digital");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Certificados P12", "*.p12")
        );

        File file = fileChooser.showOpenDialog(txtCertificadoPath.getScene().getWindow());
        if (file != null) {
            txtCertificadoPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void abrirConfiguracionSRI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/ConfiguracionSRI.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Configuración SRI");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception ex) {
            mostrarAlerta("Error", "Error al abrir la configuración del SRI: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelar() {
        Stage stage = (Stage) txtRuc.getScene().getWindow();
        stage.close();
    }

    private boolean validarCampos() {
        if (txtRuc.getText().isEmpty() || txtRuc.getText().length() != 13) {
            mostrarAlerta("Error", "El RUC debe tener 13 dígitos", Alert.AlertType.ERROR);
            return false;
        }
        if (txtRazonSocial.getText().isEmpty()) {
            mostrarAlerta("Error", "La razón social es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (txtDireccion.getText().isEmpty()) {
            mostrarAlerta("Error", "La dirección es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (txtCodigoEstablecimiento.getText().isEmpty() || txtCodigoEstablecimiento.getText().length() != 3) {
            mostrarAlerta("Error", "El código de establecimiento debe tener 3 dígitos", Alert.AlertType.ERROR);
            return false;
        }
        if (txtPuntoEmision.getText().isEmpty() || txtPuntoEmision.getText().length() != 3) {
            mostrarAlerta("Error", "El punto de emisión debe tener 3 dígitos", Alert.AlertType.ERROR);
            return false;
        }
        if (cmbTipoAmbiente.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de ambiente", Alert.AlertType.ERROR);
            return false;
        }
        if (txtCertificadoPath.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar el certificado digital", Alert.AlertType.ERROR);
            return false;
        }
        if (txtContrasena.getText().isEmpty()) {
            mostrarAlerta("Error", "La contraseña del certificado es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtRuc.clear();
        txtRazonSocial.clear();
        txtNombreComercial.clear();
        txtDireccion.clear();
        txtCodigoEstablecimiento.clear();
        txtPuntoEmision.clear();
        cmbTipoAmbiente.setValue(null);
        txtCertificadoPath.clear();
        txtContrasena.clear();
        chkObligadoContabilidad.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
