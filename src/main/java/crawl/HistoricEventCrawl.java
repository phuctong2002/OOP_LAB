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
    private String time;
    private String name;
    private List<String> relatedInformation = new ArrayList<>();
    private String summary;


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
                    time = getTime(element);
                    name = getName(element);
                    if (name.equals(time)) {
                        Element dl = element.nextElementSibling();
                        Elements dd = dl.select("dd");
                        arr.add(getHistoricEvent(dd));
                    } else {
                        relatedInformation = getRelatedInformation(element);
                        summary = getSummary(element);
                        arr.add(getHistoricEvent());
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
        List<String> relatedInformation = new ArrayList<>();
        Elements a = element.select("a");
        for (Element i : a) {
            if (!(i.text().equals(name)))
                relatedInformation.add(i.text());
        }
        return relatedInformation;
    }


    private String getTime(Element element) {
        return element.select("b").text();
    }

    private String getName(Element element) {
        return element.text().replace(time + " ", "");
    }

    private  String getSummary(Element element)  {
        StringBuilder summary = new StringBuilder();
        try {
            Elements a = element.select("a");
            if (a.size() > 1) return null;
            String url = a.attr("href");
            Document document = Jsoup.connect("https://vi.wikipedia.org" + url).get();
            Element p = document.select(".mw-parser-output > p").first();
            while (p.tagName().equals("p")) {
                summary.append(p.text());
                p = p.nextElementSibling();
            }

        }catch (Exception e){

        }
        return summary.toString();
    }

    public JSONObject getHistoricEvent() {
        JSONObject obj = new JSONObject();
        obj.put("id", "Historic Event " + qty);
        obj.put("History Event", name);
        obj.put("Time", time);
        obj.put("Related Information", relatedInformation);
        obj.put("Summary", summary);
        System.out.println(obj);
        return obj;
    }


    public JSONArray getHistoricEvent(Elements elements) {
        JSONArray jsonArray = new JSONArray();
        for (Element element : elements) {
            time = getTime(element) + " năm " + time;
            name = getName(element);
            relatedInformation = getRelatedInformation(element);
            summary = getSummary(element);
            jsonArray.add(getHistoricEvent());
        }
        return jsonArray;
    }

}
