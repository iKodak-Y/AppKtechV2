package com.ktech.appktechv2.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class PrincipalController implements Initializable {

    @FXML
    private Button btn_reportes;
    @FXML
    private Button btn_facturacion;
    @FXML
    private Button btn_venta;
    @FXML
    private Button btn_emisor;

    private MainLayoutController mainLayoutController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setMainLayoutController(MainLayoutController mainLayoutController) {
        this.mainLayoutController = mainLayoutController;
    }

    @FXML
    private void acc_facturacion(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/Factura.fxml");
    }

    @FXML
    private void acc_venta(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/TicketsVentas.fxml");
    }

    @FXML
    private void acc_emisor(ActionEvent event) {
        loadView("/com/ktech/appktechv2/vista/Registro_Emisor.fxml");
    }


    private void loadView(String fxmlPath) {
        if (mainLayoutController == null) {
            System.out.println("MainLayoutController no está configurado");
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

    @FXML
    private void acc_reportes(ActionEvent event) {
    }
}
