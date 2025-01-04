module com.ktech.appktechv2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.ktech.appktechv2 to javafx.fxml;
    exports com.ktech.appktechv2;
}
