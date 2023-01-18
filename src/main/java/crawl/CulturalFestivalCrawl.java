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
import java.util.*;

public class CulturalFestivalCrawl {
    private final String fileData = "data/CulturalFestival.json";
    private String source;
    private static int qty = 0;
    private static String time;
    private static String name;
    private static String location;
    private static String firstTime;
    private static List<String> relatedCharacter = new ArrayList<>();
    private static int relatedHistoricalSites;
    private static String summary;


    public CulturalFestivalCrawl(String source) {
        this.source = source;
    }

    public void getData() {
        List<String> url = ListURL.getURL(source);
        JSONArray arr = new JSONArray();
        assert url != null;
        for (String s : url) {
            JSONObject obj = new JSONObject();
            try {
                Document document = Jsoup.connect(s).get();
                Elements table = document.getElementsByClass("prettytable wikitable");
                Elements elements = Objects.requireNonNull(table.first()).getElementsByTag("td");
                for (int j = 2; j < elements.size(); j += 6) {
                    time = getTime(elements.get(j));
                    location = getLocation(elements.get(j + 1));
                    name = getName(elements.get(j + 2));
                    summary = getSummary(elements.get(j + 2));
                    firstTime = getFirstTime(elements.get(j + 3));
                    relatedCharacter = getRelatedCharacter(elements.get(j + 4));
//                    relatedHistoricalSites
                    ++qty;
                    obj.put("id", "Cultural Festival" + qty);
                    obj.put("name", name);
                    obj.put("time", time);
                    obj.put("location", location);
                    obj.put("first time", firstTime);
                    obj.put("related Character", relatedCharacter);
                    obj.put("summary", summary);
                    arr.add(obj);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JsonHandler.writeJsonFile(arr, fileData);
    }

    private List<String> getRelatedCharacter(Element element) {
        List<String> relatedCharacter = new ArrayList<>();
        String a = element.select("td").text();
        Collections.addAll(relatedCharacter, a.split(", ", 0));
        return relatedCharacter;
    }



    private String getTime(Element element) {
        return element.select("td").text();
    }
    private String getFirstTime(Element element) {
        return element.select("td").text();
    }
    public static String getLocation(Element element) {
        return element.select("td").text();
    }

    private String getName(Element element) {
        return element.select("td").text();
    }

    private  String getSummary(Element element)  {
        StringBuilder summary = new StringBuilder();
        try {
            Elements a = element.select("a");
            if (a.size() > 1) return null;
            String url = a.attr("href");
            Document document = Jsoup.connect("https://vi.wikipedia.org" + url).get();
            Element p = document.select(".mw-parser-output > p").first();
            while (true) {
                assert p != null;
                if (!p.tagName().equals("p")) break;
                summary.append(p.text());
                p = p.nextElementSibling();
            }

        }catch (Exception ignored){

        }
        return summary.toString();
    }

}