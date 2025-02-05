package com.ktech.appktechv2.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConfigController implements Initializable {

    @FXML
    private Button btn_registerUser;
    @FXML
    private Button btn_registerEmail;
    private MainLayoutController mainLayoutController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void navigateToRegisterUser(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/Register.fxml");
    }

    @FXML
    private void navigateToRegisterEmal(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/Register_Email.fxml");
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void loadView(String fxmlPath) {
        if (mainLayoutController == null) {
            System.out.println("Config Vista no está configurado");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            mainLayoutController.setContent(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainLayoutController(MainLayoutController controller) {
        this.mainLayoutController = controller;
    }
}
