module com.example.ex3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.ex3 to javafx.fxml;
    opens com.example.ex3.view to javafx.fxml;
    exports com.example.ex3;
}