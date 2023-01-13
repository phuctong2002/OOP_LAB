package crawl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;
import util.ListURL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricEventCrawl {
    private final String fileData = "data/HistoricEvent.json";
    private String source;
    private int qty = 0;

    public HistoricEventCrawl(String source) {
        this.source = source;
    }

    public void getData() {
        List<String> url = ListURL.getURL(source);
        JSONArray arr = new JSONArray();
        for (int i = 0; i < url.size(); ++i) {
            try {
                Document document = Jsoup.connect(url.get(i)).get();
                Elements elements = document.select(".mw-parser-output > p");
                for (int j = 1; j < elements.size(); ++j) {
                    Element element = elements.get(j);

                    if(getName(element).equals(getTime(element))){
                        Element dl = element.nextElementSibling();
//                        System.out.println(dl.text());
                        Elements dd = dl.select("dd");
//                        System.out.println(dd.text());
                        arr.add(getHistoricEvent(dd, getTime(element)));
                    }else{
                        arr.add(getHistoricEvent(element));
                    }
                    ++qty;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JsonHandler.writeJsonFile(arr, fileData);
    }

    private List<String> getRelatedInformation(Element element) {
        List<String> relatedCharacters = new ArrayList<>();
        Elements a = element.select("a");
        for (Element i : a) {
            relatedCharacters.add(i.text());
        }
        return relatedCharacters;
    }

    private String getTime(Element element, String time) {
        return element.select("b").text() + " năm "+ time;
    }

    private String getTime(Element element) {
        return element.select("b").text();
    }

    private String getName(Element element) {
        return element.text().replace(element.select("b").text() + " ","");
    }

    private  String getSummary(Element element)  {
        String summary = null;
        try {
            Elements a = element.select("a");
            if(a.size() > 1) return  null;
            String url = a.attr("href");
            Document document = Jsoup.connect("https://vi.wikipedia.org" + url).get();
            Element header = document.select(".mw-parser-output > p").first();
            summary = header.text();
        }catch (Exception e){

        }
        return  summary;
    }

    public JSONObject getHistoricEvent(Element element) {
        JSONObject obj = new JSONObject();
        obj.put("id", "Historic Event " + qty);
        obj.put( "History Event", getName(element));
        obj.put("Time","Năm " + getTime(element));
        obj.put("Related Information", getRelatedInformation(element));
        obj.put("Summary", getSummary(element));
        System.out.println(obj);
        return  obj;
    }

    public JSONObject getHistoricEvent(Element element, String time) {
        JSONObject obj = new JSONObject();
        obj.put("id", "Historic Event " + qty);
        obj.put( "History Event", getName(element) );
        obj.put("Time", getTime(element)+ " năm " + time);
        obj.put("Related Information", getRelatedInformation(element));
        obj.put("Summary", getSummary(element));
        System.out.println(obj);
        return  obj;
    }

    public JSONArray getHistoricEvent(Elements elements, String time) {
        JSONArray jsonArray = new JSONArray();
        for(Element element : elements){
            jsonArray.add(getHistoricEvent(element, time));
        }
        return  jsonArray;
    }







}
