package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.JsonHandler;

import java.io.IOException;
import java.util.regex.Pattern;

public class HistoricalFigureCrawler {
    private static final String FILE_DATA = "/data/HistoricalFigure.json";
    private static int qty = 0;

    public HistoricalFigureCrawler() {
        getData();
    }

    public void getData() {
        String url = "https://nguoikesu.com/nhan-vat";
        JSONArray arr = new JSONArray();
        do {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements list1 = doc.select(".blog-item");
                Elements list2 = doc.select(".com-content-category-blog__pagination ul.pagination>li");
                for (int i = 0; i < list1.size(); i++) {
                    JSONObject obj = new JSONObject();
                    if (list1.get(i).select("h2>a").text().contains("nhà")) continue;
                    if (list1.get(i).select("h2>a").text().contains("Nhà")) continue;
                    System.out.println(list1.get(i).select("h2>a").text());
                    obj.put("tên", list1.get(i).select("h2>a").text());
                    Document detail = Jsoup.connect("https://nguoikesu.com" + list1.get(i).select("h2>a").attr("href")).get();
                    try {
                        Element infoBox = detail.select(".infobox").get(0);
                        Elements info = infoBox.select("tbody>tr:has(>td):has(>th)");
                        for (int j = 0; j < info.size(); ++j) {
                            String key = info.get(j).select(">th").first().text();
                            String value = info.get(j).select(">td").first().text();
                            obj.put(key, value);
                        }
                        obj.put("tóm tắt", getBrief(detail));
                    } catch (Exception e) {
                        obj.put("năm sinh", getStartedTime(detail));
                        obj.put("năm mất", getEndedTime(detail));
                        obj.put("tóm tắt", getBrief(detail));
                    }
                    arr.add(obj);
                }
                Elements links = list2.get(list2.size() - 2).select("a");
                if (links.size() == 0) break;
                url = "https://nguoikesu.com" + list2.get(list2.size() - 2).select("a").attr("href");
                System.out.println(url);
            } catch (IOException e) {
                break;
            }

        } while (true);
        JsonHandler.writeJsonFile(arr, FILE_DATA);
    }

    private String getBrief(Document detail) {
        Element brief = detail.select(".com-content-article__body>p").first();
        return brief.text();
    }

    private String getEndedTime(Document detail) {
        String result = "";
        Elements infobox = detail.select(".infobox");
        if (infobox.size() != 0) {
            Element info = infobox.get(0);
            Element tmp = info.select("tr:has(th:contains(Mất))").select("tr:not(:has(th:contains(khai)))").select("td").first();
            if (tmp != null) {
                result = tmp.firstChild().toString().trim();
                if (!Pattern.compile("[0-9]").matcher(result).find()) {
                    if (!result.contains("?"))
                        result = "";
                }
                if (tmp.firstChild().childNodeSize() != 0) {
                    result = "";
                }
            }
        }
        if (result.length() == 0) {
            Element info = detail.select(".com-content-article__body").get(0).select(">p").first();
            if( info != null){

                String tmp = info.text();
                int index1 = tmp.indexOf('(');
                int index2 = tmp.indexOf('-');
                int index22 = tmp.indexOf(8211);
                int index3 = tmp.indexOf(')');
                if (index1 == -1) {
                    result = "";
                } else if (index3 == -1) {
                    result = "";
                } else if (index2 == -1) {
                    if (index22 == -1)
                        result = "";
                    else if (index22 < index3)
                        result = tmp.substring(index22 + 1, index3);
                    else result = "";
                } else {
                    if (index2 < index3)
                        result = tmp.substring(index2 + 1, index3).trim();
                    else result = "";
                }
                if (!Pattern.compile("[0-9]").matcher(result).find()) {
                    if (!result.contains("?"))
                        result = "";
                }
            }
        }
        return result;
    }

    private String getStartedTime(Document detail) {
        String result = "";
        Elements infobox = detail.select(".infobox");
        if (infobox.size() != 0) {
            Element info = infobox.get(0);
            Element tmp = info.select("tr:has(th:contains(Sinh))").select("tr:not(:has(th:contains(khai)))").select("td").first();
            if (tmp != null) {
                result = tmp.firstChild().toString().trim();
                if (!Pattern.compile("[0-9]").matcher(result).find()) {
                    if (!result.contains("?"))
                        result = "";
                }
                if (tmp.firstChild().childNodeSize() != 0) {
                    result = "";
                }
            }
        }
        if (result.length() == 0) {
            Element info = detail.select(".com-content-article__body").get(0).select(">p").first();
            if( info != null){
                String tmp = info.text();
                int index1 = tmp.indexOf('(');
                int index22 = tmp.indexOf(8211);
                int index2 = tmp.indexOf('-');
                int index3 = tmp.indexOf(')');
                if (index1 == -1) {
                    result = "";
                } else if (index3 == -1) {
                    result = "";
                } else if (index2 == -1) {
                    if (index22 == -1)
                        result = tmp.substring(index1 + 1, index3).trim();
                    else
                        result = tmp.substring(index1 + 1, index22).trim();
                } else {
                    if (index1 < index2)
                        result = tmp.substring(index1 + 1, index2).trim();
                }
                if (!Pattern.compile("[0-9]").matcher(result).find()) {
                    if (!result.contains("?"))
                        result = "";
                }
            }
        }
        return result;
    }
}
