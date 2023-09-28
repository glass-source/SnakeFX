module com.asunder.snakefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.asunder.snakefx to javafx.fxml;
    exports com.asunder.snakefx;
}