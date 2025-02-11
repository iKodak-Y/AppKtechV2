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
            // ----------- CONEXION BASE DE DATOS   --------------
            SqlConnection conexion = new SqlConnection(); // Crear una instancia de SqlConection
            Connection con = conexion.getConexion(); // Intentar conectar a la base de datos
            // Verificar si la conexión fue exitosa
            if (con != null) {
                System.out.println("Conexion establecida con exito.");
            } else {
                System.out.println("Main: Fallo al establecer conexión con la base de datos.");
            }

            // ---------   CONFIGURACION PANTALLA     -------------- 
            System.out.println("Iniciando aplicación...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/MainLayout.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("AppKtech");
            stage.setMaximized(true);
            stage.setResizable(true);

            stage.show();
            System.out.println("Aplicación iniciada correctamente");
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
