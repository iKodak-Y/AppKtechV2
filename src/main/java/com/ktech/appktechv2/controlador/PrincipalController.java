package com.ktech.appktechv2.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

    @FXML
    private Button btn_clientes;
    @FXML
    private Button btn_productos;
    @FXML
    private Button btn_reportes;
    @FXML
    private Button btn_facturacion;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Button btn_venta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void acc_clientes(ActionEvent event) {
        try {
            String formulario = "/com/ktech/appktechv2/vista/Clientes.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Parent root = loader.load();

            ClientesController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setResizable(false);
            stage.show();

            Stage myStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            if (myStage != null) {
                myStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acc_productos(ActionEvent event) {
        try {
            String formulario = "/com/ktech/appktechv2/vista/Gestion_Productos.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Parent root = loader.load();

            Gestion_ProductosController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setResizable(false);
            stage.show();

            Stage myStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            if (myStage != null) {
                myStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acc_reportes(ActionEvent event) {
    }

    @FXML
    private void acc_facturacion(ActionEvent event) {

    }

    @FXML
    private void acc_cerrar(ActionEvent event) {
        try {
            System.exit(0);
        } catch (Exception e) {
        }
    }

    @FXML
    private void acc_venta(ActionEvent event) {
        try {
            String formulario = "/com/ktech/appktechv2/vista/TicketsVentas.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(formulario));
            Parent root = loader.load();

            TicketsVentasController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setResizable(false);
            stage.show();

            Stage myStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            if (myStage != null) {
                myStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
