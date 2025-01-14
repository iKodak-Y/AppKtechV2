package com.ktech.appktechv2;

import com.ktech.appktechv2.SqlConnection;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.sql.Connection;
import static javafx.application.Application.launch;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Crear una instancia de SqlConection
            SqlConnection conexion = new SqlConnection();

            // Intentar conectar a la base de datos
            Connection con = conexion.getConexion();

            // Verificar si la conexión fue exitosa
            if (con != null) {
                System.out.println("Conexión establecida con éxito.");
            } else {
                System.out.println("Main: Fallo al establecer conexión con la base de datos.");
            }

            // Cargar el archivo FXML desde resources
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/ktech/appktechv2/vista/principal.fxml"));
            Pane ventana = loader.load();

            // Configurar y mostrar la escena
            Scene scene = new Scene(ventana);
            stage.setScene(scene);
            stage.setTitle("Aplicación de Facturación");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());

            // Mostrar alerta al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error al iniciar la aplicación: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
