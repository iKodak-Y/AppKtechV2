package com.ktech.appktechv2.controlador;

import com.ktech.appktechv2.modelo.ConfiguracionEmail;
import com.ktech.appktechv2.modelo.ConfiguracionEmailDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

public class Register_EmailController implements Initializable {

    @FXML
    private TextField correoField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEditar;
    @FXML
    private Label errorLabel;

    private ConfiguracionEmailDAO configuracionDAO;
    private boolean modoEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            configuracionDAO = new ConfiguracionEmailDAO();
            cargarConfiguracionActual();
            deshabilitarEdicion();
        } catch (Exception e) {
            mostrarError("Error al inicializar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarConfiguracionActual() {
        try {
            ConfiguracionEmail config = configuracionDAO.obtenerConfiguracionActual();
            if (config != null) {
                correoField.setText(config.getCorreoElectronico());
                passwordField.setText(config.getPasswordApp());
            }
        } catch (Exception e) {
            mostrarError("Error al cargar la configuración: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditar(ActionEvent event) {
        try {
            if (!modoEdicion) {
                habilitarEdicion();
                mostrarMensaje("Modo edición activado", false);
            }
        } catch (Exception e) {
            mostrarError("Error al activar modo edición: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleGuardar(ActionEvent event) {
        try {
            if (modoEdicion) {
                if (validarCampos()) {
                    guardarConfiguracion();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        String correo = correoField.getText().trim();
        String password = passwordField.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor, complete todos los campos");
            return false;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarError("Por favor, ingrese un correo electrónico válido");
            return false;
        }

        return true;
    }

    private void guardarConfiguracion() {
        String correo = correoField.getText().trim();
        String password = passwordField.getText().trim();

        boolean exito = configuracionDAO.actualizarConfiguracion(correo, password);
        if (exito) {
            mostrarMensaje("Configuración guardada exitosamente", false);
            deshabilitarEdicion();
        } else {
            mostrarError("Error al guardar la configuración");
        }
    }

    private void habilitarEdicion() {
        modoEdicion = true;
        correoField.setDisable(false);
        passwordField.setDisable(false);
        correoField.setOpacity(1.0);
        passwordField.setOpacity(1.0);
        btnGuardar.setDisable(false);
        btnEditar.setDisable(true);
    }

    private void deshabilitarEdicion() {
        modoEdicion = false;
        correoField.setDisable(true);
        passwordField.setDisable(true);
        correoField.setOpacity(0.7);
        passwordField.setOpacity(0.7);
        btnGuardar.setDisable(true);
        btnEditar.setDisable(false);
    }

    private void mostrarError(String mensaje) {
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText(mensaje);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        errorLabel.setTextFill(esError ? Color.RED : Color.GREEN);
        errorLabel.setText(mensaje);
    }
}
