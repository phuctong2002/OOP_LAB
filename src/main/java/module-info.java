module com.example.finalcrawldata {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.jsoup;


    opens Main to javafx.fxml;
    exports Main;
    opens util to json.simple;
    exports controller;
    opens controller to javafx.fxml;
}