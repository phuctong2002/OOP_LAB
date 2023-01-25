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
import java.util.List;

public class HistoricEventCrawl {
    private static final String FILE_DATA = "/data/HistoricEvent.json";
    JSONArray arr = new JSONArray();
    private String time;
    private String name;
    private List<String> relatedInformation = new ArrayList<>();
    private String summary;


    public HistoricEventCrawl() {
        getData();
    }

    public void getData() {
        try {
            Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam").get();
            Elements elements = document.select(".mw-parser-output > p");
            System.out.println( elements.size());
            for (int j = 1; j < elements.size(); ++j) {
                Element element = elements.get(j);
                time = getTime(element);
                name = getName(element);
                if (name.equals(time)) {
                    Element dl = element.nextElementSibling();
                    Elements dd = dl.select("dd");
                    getListHistoricEvent(dd);

                } else {
                    relatedInformation = getRelatedInformation(element);
                    summary = getSummary(element);
                    arr.add(getHistoricEvent());
                }
                System.out.println(j);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonHandler.writeJsonFile(arr, FILE_DATA);
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

    private String getSummary(Element element) {
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

        } catch (Exception e) {

        }
        return summary.toString();
    }

    public JSONObject getHistoricEvent() {
        JSONObject obj = new JSONObject();
        obj.put("tên", name);
        obj.put("thời gian", time);
        obj.put("thông tin liên quan", relatedInformation);
        obj.put("tóm tắt", summary);
        return obj;
    }


    public void getListHistoricEvent(Elements elements) {
        for (Element element : elements) {
            time = getTime(element) + " năm " + time;
            name = getName(element);
            relatedInformation = getRelatedInformation(element);
            summary = getSummary(element);
            arr.add(getHistoricEvent());
        }
    }
}
