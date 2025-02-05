module com.ktech.appktechv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mssql.jdbc;
    requires java.xml.bind;
    requires java.activation;
    requires java.desktop;
    requires java.mail;
    
    // iText 7 modules
    requires kernel;
    requires io;
    requires layout;
    requires forms;
    requires java.base;
    
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
