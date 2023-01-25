package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.JsonHandler;

import java.io.IOException;

public class HistoricalSite {
    private final static String FILE_DATA = "/data/HistoricalSites.json";

    public HistoricalSite() {
        getData();
    }

    public void getData() {
        String base = "https://nguoikesu.com/dia-danh";
        int index = 0;
        JSONArray arr = new JSONArray();
        while (true) {
            String url = base + "?start=" + index;
            try {
                Document doc = Jsoup.connect(url).get();
                Elements com = doc.select(".com-content-category-blog__item");
                if (com.size() == 0) break;
                for (int i = 0; i < com.size(); i++) {
                    if (com.get(i).select("[itemprop=url]").size() == 0) continue;
                    Document detail = Jsoup.connect("https://nguoikesu.com" + com.get(i).select("[itemprop=url]").get(0).attr("href")).get();
                    Elements info = detail.select("div.infobox");
                    if (info.size() != 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("tên", com.get(i).select("[itemprop=url]").get(0).text());
                        System.out.println(com.get(i).select("[itemprop=url]").get(0).text());
                        // duyet info box o day nhe
                        Elements els = info.get(0).select(">table>tbody>tr:has(>th):has(>td)");
                        for (int j = 0; j < els.size(); ++j) {
                            String key = els.get(j).select(">th").get(0).text();
                            String value = els.get(j).select(">td").text();
                            obj.put(key, value);
                        }
                        System.out.println(els.size());
                        arr.add(obj);
                    }
                }
            } catch (IOException e) {
                System.out.println("End");
            }
            index += 5;
        }
        JsonHandler.writeJsonFile(arr, FILE_DATA);
    }
}