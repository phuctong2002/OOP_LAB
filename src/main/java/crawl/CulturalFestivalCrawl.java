package crawl;

import javafx.util.Pair;
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
    private String source;
    private static int qty = 0;
//    private static int relatedHistoricalSites;


    public CulturalFestivalCrawl(String source) {
        this.source = source;
    }

    public void getData() {
        List<String> url = ListURL.getURL(source);
        JSONArray arr = new JSONArray();
        assert url != null;
        for (String s : url) {
            try {
                Document document = Jsoup.connect(s).get();
                Elements table = document.getElementsByClass("prettytable wikitable");
                Elements elements = Objects.requireNonNull(table.first()).getElementsByTag("tr");
                for (int j = 1; j < elements.size(); j++) {
                    Elements d = elements.get(j).select("td");
                    String time = getTime(d.get(0));
                    String location = getLocation(d.get(1));
                    String name = getName(d.get(2));
                    String summary = getSummary(d.get(2));
                    String firstTime = getFirstTime(d.get(3));
                    List<String> relatedCharacter = getRelatedCharacter(d.get(4));
//                    relatedHistoricalSites
                    ++qty;
                    JSONObject obj = new JSONObject();
                    obj.put("id", "Cultural Festival" + qty);
                    obj.put("name", name);
                    obj.put("time", time);
                    obj.put("location", location);
                    obj.put("first time", firstTime);
                    obj.put("related Character", relatedCharacter);
                    obj.put("summary", summary);
                    arr.add(obj);

                }
                //
                Elements table2 = document.getElementsByClass("mw-parser-output");
                Elements tds = Objects.requireNonNull(table2.first()).getElementsByTag("ul");
                Elements td = tds.get(10).select("li");
                for (Element element : td) {
                    ++qty;
                    //tach dia diem, le hoi
                    List<String> list = new ArrayList<>();
                    String a = element.select("li").text();
                    Collections.addAll(list, a.split(": ", 0));
                    String location = list.get(0);
//                    obj.put("Location", location);
                    // tach le hoi
                    List<String> listFes = new ArrayList<>();
                    String tmp = list.get(1).replaceAll("\\(.*?\\)","");
                    Collections.addAll(listFes, tmp.split("[,\\;]", 0));
                    // tach ngay
//                    int cnt = 1;
                    for (int k = 0; k < listFes.size(); k++) {
                        JSONObject obj = new JSONObject();
                        String str = listFes.get(k).replaceAll("[^0-9/-]", " ");
                        List<String> m = new ArrayList<>();
                        Collections.addAll(m, str.split(" ", 0));
                        obj.put("Name", listFes.get(k));
                        if (m.size() != 0) {
                            obj.put("Time", m.get(m.size() - 1));
                        } else {
                            obj.put("Time", null);
                        }
                        obj.put("Location", location);
                        obj.put("id", "Cultural Festival" + qty);
                        obj.put("first time", null);
                        obj.put("related Character", null);
                        obj.put("summary", null);
                        arr.add(obj);
//                        cnt++;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        String fileData = "data/CulturalFesData.json";
        JsonHandler.writeJsonFile(arr, fileData);
    }

    private List<String> getRelatedCharacter(Element element) {
        List<String> relatedCharacter = new ArrayList<>();
        String a = element.select("td").text();
        Collections.addAll(relatedCharacter, a.split(", ", 0));
        return relatedCharacter;
    }


    private String getTime(Element element) {
        return element.text();
    }

    private String getFirstTime(Element element) {
        return element.text();
    }

    public static String getLocation(Element element) {
        return element.text();
    }

    private String getName(Element element) {
        return element.text();
    }

    private String getSummary(Element element) {
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

        } catch (Exception ignored) {

        }
        return summary.toString();
    }

}