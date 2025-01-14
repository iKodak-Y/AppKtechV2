module com.ktech.appktechv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql; 

    opens com.ktech.appktechv2 to javafx.fxml;    
    opens com.ktech.appktechv2.controlador to javafx.fxml;
    opens com.ktech.appktechv2.modelo to javafx.base;
    
    exports com.ktech.appktechv2;
    exports com.ktech.appktechv2.modelo;
    exports com.ktech.appktechv2.controlador;

}
