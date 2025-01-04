module com.ktech.appktechv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql; 

    opens com.ktech.appktechv2.controlador to javafx.fxml;
    opens com.ktech.appktechv2.modelo to javafx.base;
    
    opens com.ktech.appktechv2 to javafx.fxml;
    exports com.ktech.appktechv2;
}
