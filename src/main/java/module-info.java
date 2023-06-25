module justinduross {
    requires javafx.controls;
    requires javafx.fxml;

    opens justinduross to javafx.fxml;
    exports justinduross;
}
