module com.ktech.appktechv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mssql.jdbc;
    requires java.xml.bind;
    requires java.activation;
    requires java.desktop;
    
    opens com.ktech.appktechv2 to javafx.fxml;
    opens com.ktech.appktechv2.controlador to javafx.fxml;
    opens com.ktech.appktechv2.modelo to java.xml.bind;
    opens com.ktech.appktechv2.modelo.xml to java.xml.bind;
    
    exports com.ktech.appktechv2;
    exports com.ktech.appktechv2.controlador;
    exports com.ktech.appktechv2.modelo;
    exports com.ktech.appktechv2.modelo.xml;
    exports com.ktech.appktechv2.util;
}