package crawl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.JsonHandler;
import util.ListURL;

import java.io.IOException;
import java.util.List;

public class DynastyCrawl {
    private final String fileData = "data/DynastyData.json";
    private String source;
    private int qty = 0;
    public DynastyCrawl( String source){
        this.source = source;
    }
    public void getData( ){
        List<String> url = ListURL.getURL(source);
        JSONArray arr = new JSONArray();
        for( int i = 0; i < url.size(); ++i){
            JSONObject obj = new JSONObject();
            try {
                Document document = Jsoup.connect( url.get(i)).get();
                obj.put("id", "DYNASTY" + qty);
                obj.put( "name", getName( document));
                obj.put("start", getStartedTime());
                System.out.println(obj);
                arr.add(obj);
                ++qty;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        JsonHandler.writeJsonFile(arr,fileData);
    }

    private String getName( Document document){
        Elements name = document.getElementsByClass("item-page");
        return name.get(0).text();
    }

    private String getStartedTime(){

        return "Start time " + qty;
    }

    private String getEndedTime(){
        return null;
    }

    private String getKing(){
        return null;
    }


    private String getBrief(){
        return null;
    }
}
