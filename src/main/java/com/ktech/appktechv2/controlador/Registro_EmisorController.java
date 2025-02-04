package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import java.io.FileInputStream;
import java.io.IOException;

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
    @FXML
    private Button btnEditar; // Nuevo botón "Editar"
    private MainLayoutController mainLayoutController;
    private boolean modoEdicion = false; // Indica si estamos en modo edición
    private final SqlConnection sqlConnection = new SqlConnection();
    @FXML
    private Button btnSeleccionarCertificado;
    private String rucOriginal;
    @FXML
    private TextField txtLogoPath;
    @FXML
    private Button btnSeleccionarLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar ComboBox de tipo ambiente
        cmbTipoAmbiente.setItems(FXCollections.observableArrayList(
                "1 - Pruebas",
                "2 - Producción"
        ));

        // Validaciones
        setupValidations();

        // Cargar datos del emisor si existe
        cargarDatosEmisor();
    }

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }

    private void setupValidations() {
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

    private void cargarDatosEmisor() {
        try (Connection conn = sqlConnection.getConexion()) {
            String sql = "SELECT * FROM Emisor";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rucOriginal = rs.getString("ruc"); // Guardar el RUC original
                txtRuc.setText(rucOriginal);
                txtRazonSocial.setText(rs.getString("razon_social"));
                txtNombreComercial.setText(rs.getString("nombre_comercial"));
                txtDireccion.setText(rs.getString("direccion"));
                txtCodigoEstablecimiento.setText(rs.getString("codigo_establecimiento"));
                txtPuntoEmision.setText(rs.getString("punto_emision"));
                cmbTipoAmbiente.setValue(rs.getString("tipo_ambiente").equals("1") ? "1 - Pruebas" : "2 - Producción");
                chkObligadoContabilidad.setSelected(rs.getBoolean("obligado_contabilidad"));
                txtCertificadoPath.setText(rs.getString("certificado_path"));
                txtContrasena.setText(rs.getString("contrasena_certificado"));
                txtLogoPath.setText(rs.getString("Logo")); // Mostrar la ruta del logotipo
                bloquearCampos(true); // Bloquear campos después de cargar los datos
                btnEditar.setVisible(true);
            } else {
                btnEditar.setVisible(false);
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al cargar los datos del emisor: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void guardarEmisor() {
        if (!validarCampos()) {
            return;
        }

        try (Connection conn = sqlConnection.getConexion()) {
            String sql;
            if (modoEdicion) {
                sql = "UPDATE Emisor SET ruc = ?, razon_social = ?, nombre_comercial = ?, direccion = ?, "
                        + "codigo_establecimiento = ?, punto_emision = ?, tipo_ambiente = ?, obligado_contabilidad = ?, "
                        + "certificado_path = ?, contrasena_certificado = ? ";

                // Solo incluir el campo Logo si hay un nuevo archivo seleccionado
                File logoFile = new File(txtLogoPath.getText());
                if (logoFile.exists()) {
                    sql += ", Logo = ?";
                }

                sql += " WHERE ruc = ?";
            } else {
                sql = "INSERT INTO Emisor (ruc, razon_social, nombre_comercial, direccion, "
                        + "codigo_establecimiento, punto_emision, tipo_ambiente, obligado_contabilidad, "
                        + "certificado_path, contrasena_certificado, Logo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }

            PreparedStatement stmt = conn.prepareStatement(sql);

            // Configurar los parámetros
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

            // Manejar el logotipo
            File logoFile = new File(txtLogoPath.getText());
            if (modoEdicion && logoFile.exists()) {
                byte[] logoBytes = leerArchivoComoBytes(logoFile);
                stmt.setBytes(11, logoBytes);
                stmt.setString(12, rucOriginal); // Usar el RUC original para identificar el registro
            } else if (!modoEdicion) {
                byte[] logoBytes = logoFile.exists() ? leerArchivoComoBytes(logoFile) : null;
                if (logoBytes != null) {
                    stmt.setBytes(11, logoBytes);
                } else {
                    stmt.setNull(11, java.sql.Types.BLOB); // Si no hay logotipo, establecer NULL
                }
            } else if (modoEdicion) {
                stmt.setString(11, rucOriginal); // Usar el RUC original para identificar el registro
            }

            stmt.executeUpdate();

            if (mainLayoutController != null) {
                mainLayoutController.actualizarNombreComercial();
            }

            mostrarAlerta("Éxito", "Emisor guardado correctamente", Alert.AlertType.INFORMATION);
            cargarDatosEmisor(); // Recargar los datos desde la base de datos
            bloquearCampos(true); // Bloquear campos después de guardar
            modoEdicion = false;
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al guardar el emisor: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void editarEmisor() {
        modoEdicion = true;
        bloquearCampos(false);
    }

    @FXML
    private void cancelarEdicion() {
        modoEdicion = false;
        cargarDatosEmisor(); // Restaurar los datos originales
        bloquearCampos(true);
    }

    private void bloquearCampos(boolean bloquear) {
        txtRuc.setDisable(bloquear);
        txtRazonSocial.setDisable(bloquear);
        txtNombreComercial.setDisable(bloquear);
        txtLogoPath.setDisable(bloquear);
        txtDireccion.setDisable(bloquear);
        txtCodigoEstablecimiento.setDisable(bloquear);
        txtPuntoEmision.setDisable(bloquear);
        cmbTipoAmbiente.setDisable(bloquear);
        txtCertificadoPath.setDisable(bloquear);
        txtContrasena.setDisable(bloquear);
        chkObligadoContabilidad.setDisable(bloquear);

        // Cambiar estilo visual
        String estilo = bloquear ? "-fx-opacity: 0.7;" : "-fx-opacity: 1;";
        txtRuc.setStyle(estilo);
        txtRazonSocial.setStyle(estilo);
        txtNombreComercial.setStyle(estilo);
        txtLogoPath.setStyle(estilo);
        txtDireccion.setStyle(estilo);
        txtCodigoEstablecimiento.setStyle(estilo);
        txtPuntoEmision.setStyle(estilo);
        cmbTipoAmbiente.setStyle(estilo);
        txtCertificadoPath.setStyle(estilo);
        txtContrasena.setStyle(estilo);
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
        if (txtLogoPath.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un logotipo.", Alert.AlertType.ERROR);
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
        txtLogoPath.clear();
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
    private void seleccionarLogo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Logotipo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Logotipo", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(txtLogoPath.getScene().getWindow());
        if (file != null) {
            txtLogoPath.setText(file.getAbsolutePath());
        }
    }

    private byte[] leerArchivoComoBytes(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return fis.readAllBytes();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo leer el archivo: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }
}
