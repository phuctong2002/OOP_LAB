package entity;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class HistoricEvent {
    private  static  int qty = 0;
    private int id;
    private String name;
    private String time;
    private List<String> relatedInformation = new ArrayList<>();
    private String summary;

    public HistoricEvent(){

    }
    public HistoricEvent(String name, String time){
        this.id = qty;
        qty ++;
        this.name = name;
        this.time = time;
    }

    public HistoricEvent(String name, String time, List<String> relatedInformation){
        this(name, time);
        this.relatedInformation = relatedInformation;
    }

    public HistoricEvent(String name, String time, List<String> relatedInformation, String summary){
        this(name, time,relatedInformation);
        this.summary = summary;
    }

}
