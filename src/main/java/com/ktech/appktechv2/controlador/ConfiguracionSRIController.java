package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.SqlConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ConfiguracionSRIController implements Initializable {

    @FXML
    private TextField txtUrlPruebas;
    @FXML
    private TextField txtUrlProduccion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;

    private final SqlConnection sqlConnection = new SqlConnection();
    private Integer idConfiguracionActual = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarConfiguracionActual();
    }

    private void cargarConfiguracionActual() {
        try (Connection conn = sqlConnection.getConexion()) {
            String sql = "SELECT TOP 1 * FROM ConfiguracionSRI ORDER BY fecha_vigencia_inicio DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idConfiguracionActual = rs.getInt("id_configuracion");
                txtUrlPruebas.setText(rs.getString("url_pruebas"));
                txtUrlProduccion.setText(rs.getString("url_produccion"));
                dpFechaInicio.setValue(rs.getDate("fecha_vigencia_inicio").toLocalDate());
                if (rs.getDate("fecha_vigencia_fin") != null) {
                    dpFechaFin.setValue(rs.getDate("fecha_vigencia_fin").toLocalDate());
                }
                txtDescripcion.setText(rs.getString("descripcion"));
            } else {
                // Valores por defecto para URLs del SRI
                txtUrlPruebas.setText("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
                txtUrlProduccion.setText("https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
                dpFechaInicio.setValue(LocalDate.now());
            }
        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al cargar la configuración: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void guardarConfiguracion() {
        if (!validarCampos()) {
            return;
        }

        try (Connection conn = sqlConnection.getConexion()) {
            // Si hay una configuración actual, actualizamos su fecha de fin
            if (idConfiguracionActual != null) {
                String sqlUpdate = "UPDATE ConfiguracionSRI SET fecha_vigencia_fin = ? WHERE id_configuracion = ?";
                PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
                stmtUpdate.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                stmtUpdate.setInt(2, idConfiguracionActual);
                stmtUpdate.executeUpdate();
            }

            // Insertamos la nueva configuración
            String sql = "INSERT INTO ConfiguracionSRI (url_pruebas, url_produccion, fecha_vigencia_inicio, "
                    + "fecha_vigencia_fin, descripcion) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, txtUrlPruebas.getText());
            stmt.setString(2, txtUrlProduccion.getText());
            stmt.setDate(3, java.sql.Date.valueOf(dpFechaInicio.getValue()));
            stmt.setDate(4, dpFechaFin.getValue() != null ? java.sql.Date.valueOf(dpFechaFin.getValue()) : null);
            stmt.setString(5, txtDescripcion.getText());

            stmt.executeUpdate();

            mostrarAlerta("Éxito", "Configuración guardada correctamente", Alert.AlertType.INFORMATION);
            cerrarVentana();

        } catch (SQLException ex) {
            mostrarAlerta("Error", "Error al guardar la configuración: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private boolean validarCampos() {
        if (txtUrlPruebas.getText().isEmpty()) {
            mostrarAlerta("Error", "La URL de pruebas es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (txtUrlProduccion.getText().isEmpty()) {
            mostrarAlerta("Error", "La URL de producción es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (dpFechaInicio.getValue() == null) {
            mostrarAlerta("Error", "La fecha de vigencia inicio es obligatoria", Alert.AlertType.ERROR);
            return false;
        }
        if (dpFechaFin.getValue() != null && dpFechaFin.getValue().isBefore(dpFechaInicio.getValue())) {
            mostrarAlerta("Error", "La fecha de fin no puede ser anterior a la fecha de inicio", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
