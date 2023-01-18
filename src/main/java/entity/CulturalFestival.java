package entity;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class CulturalFestival {
    private  static  int qty = 0;
    private int id;
    private String name;
    private String location;
    private String time;
    private String firstTime;
    private List<String> relatedCharacter = new ArrayList<>();
    private String summary;

    public CulturalFestival(){

    }
    public CulturalFestival(String name, String time){
        this.id = qty;
        qty ++;
        this.name = name;
        this.time = time;
    }

    public CulturalFestival(String name, String time, List<String> relatedCharacter){
        this(name, time);
        this.relatedCharacter = relatedCharacter;
    }

    public CulturalFestival(String name, String time, List<String> relatedCharacter, String summary){
        this(name, time,relatedCharacter);
        this.summary = summary;
    }
    public CulturalFestival(String name, String time, List<String> relatedCharacter, String summary, String firstTime){
        this(name, time,relatedCharacter, summary);
        this.firstTime = firstTime;
    }
    public CulturalFestival(String name, String time, List<String> relatedCharacter, String summary, String firstTime, String location){
        this(name, time,relatedCharacter, summary, firstTime);
        this.location = location;
    }


}
