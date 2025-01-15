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
import java.net.URL;
import javafx.scene.CacheHint;
import javafx.scene.layout.Region;

public class App extends Application {

    public static void main(String[] args) {
        // Configuraciones básicas y probadas para mejorar el renderizado
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        System.setProperty("javafx.animation.fullspeed", "true");
        System.setProperty("javafx.animation.pulse", "60");

        // Agregar esta configuración para forzar el uso de DirectX en Windows
        System.setProperty("prism.order", "d3d,sw");

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

            // Configurar el stage para mejor renderizado
            stage.setResizable(true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ktech/appktechv2/vista/MainLayout.fxml"));
            Scene scene = new Scene(loader.load());

            // Configurar la scene para mejor renderizado
            scene.snapshot(null); // Forzar el renderizado inicial

            // Cargar el CSS
            URL cssUrl = getClass().getResource("/com/ktech/appktechv2/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }

            // Configurar DPI scaling
            scene.getRoot().setStyle("-fx-font-smoothing-type: LCD;");

            stage.setScene(scene);
            stage.setTitle("AppKtech V2");
            stage.setMaximized(true);

            // Configuración adicional para mejor renderizado
            if (scene.getRoot() instanceof Region) {
                Region root = (Region) scene.getRoot();
                root.setSnapToPixel(true);
                root.setCache(true);
                root.setCacheHint(CacheHint.QUALITY);
            }

            stage.show();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error al iniciar la aplicación: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
