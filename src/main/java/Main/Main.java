package Main;

import crawler.DynastyCrawler;
import crawler.HistoricEventCrawl;
import crawler.HistoricalFigureCrawler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home_v2.fxml"));
        Image icon = new Image(String.valueOf(getClass().getResource("/img/vietnam.png")));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        HistoricEventCrawl obj = new HistoricEventCrawl();
//        DynastyCrawler obj = new DynastyCrawler();
//        HistoricalFigureCrawler obj = new HistoricalFigureCrawler();
        launch();
    }
}