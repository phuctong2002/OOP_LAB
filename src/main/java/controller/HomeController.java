package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.Search;

import java.util.*;

public class HomeController {
    Search service;
    @FXML
    private TextField search;

    @FXML
    private ChoiceBox<String> choose;

    @FXML
    private TableView<String> tableView;

    @FXML
    private TableColumn<String, String> nameCol;
    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
        Map<String, String> map = new HashMap<>();
        map.put("Nhân vật lịch sử", "/data/HistoricalFigure.json");
        map.put("Triều đại", "/data/DynastyData.json");
        map.put("Lễ hội", "/data/CulturalFesData.json");
        map.put("Địa điểm du lịch", "/data/HistoricalSites.json");
        map.put("Sự kiện lịch sử", "/data/HistoricEvent.json");
        choose.getItems().add("Nhân vật lịch sử");
        choose.getItems().add("Triều đại");
        choose.getItems().add("Lễ hội");
        choose.getItems().add("Địa điểm du lịch");
        choose.getItems().add("Sự kiện lịch sử");
        choose.setValue(choose.getItems().get(1));
        service = new Search();
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        textArea.setWrapText( true);
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                JSONArray arr = service.search(t1.trim(), map.get(choose.getSelectionModel().getSelectedItem()));
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < arr.size(); i++) {
                    list.add((String) ((JSONObject) arr.get(i)).get("tên"));
                }
                ObservableList<String> names = FXCollections.observableArrayList(list);
                tableView.setItems(names);
            }
        });
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    JSONObject obj = (JSONObject) service.search(t1.trim(), map.get(choose.getSelectionModel().getSelectedItem())).get(0);
                    Iterator<?> keys = obj.keySet().iterator();
                    String detail = "";
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        String value = "";
                        if (obj.get(key) != null)
                            value = obj.get(key).toString();
                        detail += ("+ " + key + " : " + value + "\n\n");
                    }
                    textArea.setText(detail);
                }
            }
        });
    }
}
