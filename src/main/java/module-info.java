module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.demo1 to javafx.fxml, javafx.controls, java.sql;
    exports com.example.demo1;
}