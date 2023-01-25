package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CulturalFestivalCrawl {
    private static final String FILE_DATA = "/data/CulturalFesData.json";
    private static int qty = 0;


    public CulturalFestivalCrawl() {
        getData();
    }

    public void getData() {
        JSONArray arr = new JSONArray();
            try {
                Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#").get();
                Elements table = document.getElementsByClass("prettytable wikitable");
                Elements elements = Objects.requireNonNull(table.first()).getElementsByTag("tr");
                for (int j = 1; j < elements.size(); j++) {
                    Elements d = elements.get(j).select("td");
                    String time = d.get(0).text();
                    String location = d.get(1).text();
                    String name = d.get(2).text();
                    String summary = getSummary(d.get(2));
                    String firstTime = d.get(3).text();
                    List<String> relatedCharacter = getRelatedCharacter(d.get(4));
                    ++qty;
                    JSONObject obj = new JSONObject();
                    obj.put("tên", name);
                    obj.put("thời gian", time);
                    obj.put("địa điểm", location);
                    obj.put("tổ chức lần đầu", firstTime);
                    obj.put("nhân vật liên quan", relatedCharacter);
                    obj.put("tổng quan", summary);
                    arr.add(obj);

                }
                Elements table2 = document.getElementsByClass("mw-parser-output");
                Elements tds = Objects.requireNonNull(table2.first()).getElementsByTag("ul");
                Elements td = tds.get(10).select("li");
                for (Element element : td) {
                    ++qty;
                    List<String> list = new ArrayList<>();
                    String a = element.select("li").text();
                    Collections.addAll(list, a.split(": ", 0));
                    String location = list.get(0);
                    List<String> listFes = new ArrayList<>();
                    String tmp = list.get(1).replaceAll("\\(.*?\\)","");
                    Collections.addAll(listFes, tmp.split("[,\\;]", 0));
                    for (int k = 0; k < listFes.size(); k++) {
                        JSONObject obj = new JSONObject();
                        String str = listFes.get(k).replaceAll("[^0-9/-]", " ");
                        List<String> m = new ArrayList<>();
                        Collections.addAll(m, str.split(" ", 0));
                        obj.put("tên", listFes.get(k));
                        if (m.size() != 0) {
                            obj.put("thời gian", m.get(m.size() - 1));
                        } else {
                            obj.put("thời gian", null);
                        }
                        obj.put("địa điểm", location);
                        obj.put("tổ chức lần đầu", null);
                        obj.put("nhân vật liên quan", null);
                        obj.put("tổng quan", null);
                        arr.add(obj);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        JsonHandler.writeJsonFile(arr, FILE_DATA);
    }

    private List<String> getRelatedCharacter(Element element) {
        List<String> relatedCharacter = new ArrayList<>();
        String a = element.select("td").text();
        Collections.addAll(relatedCharacter, a.split(", ", 0));
        return relatedCharacter;
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