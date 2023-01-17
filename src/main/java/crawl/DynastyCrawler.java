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
import java.util.List;

public class DynastyCrawler {
    private final String fileData = "data/DynastyData.json";
    private static int qty = 0;
    private String source;

    public DynastyCrawler(String source) {
        this.source = source;
    }

    public void getData() {
        List<String> urls = ListURL.getURL(source);
        JSONArray arr = new JSONArray();
        for (String url : urls) {
            try {
                Document doc = Jsoup.connect(url).get();
                Elements com = doc.select(".toccolours>tbody>tr>td>table>tbody>tr");
                for (Element el : com) {
                    Elements links = el.select("b:has(a)");
                    if( links.size() == 0) continue;
                    for( Element link : links){
                        Document document = Jsoup.connect("https://vi.wikipedia.org" + link.firstChild().attr("href")).get();
                        JSONObject obj = new JSONObject();
                        ++qty;
                        obj.put("id",  qty);
                        obj.put("name", link.text());
                        obj.put("start", getStartedTime( link.nextElementSibling()));
                        obj.put("end", getEndedTime( link.nextElementSibling()));
                        obj.put("kings", getKings(document));
                        obj.put("brief", getBrief(document));
                        arr.add(obj);

                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JsonHandler.writeJsonFile(arr, fileData);
        }
    }


    public String getName(Document document) {
        Elements el = document.select(".infobox");
        if (el.size() != 0) {
            try {
                String name = el.select("tr").get(0).text();
                return name;
            } catch (Exception e) {
                System.out.println("Error");
                return null;
            }

        }
        return null;
    }

    public String getStartedTime( Element el) {
        if( el != null){
            String tmp = el.text();
            int index1 = tmp.indexOf('(');
            int index2 = tmp.indexOf(8211);
            int index3 = tmp.indexOf(')');
            if( index1 == -1) return null;
            if( index3 == -1) return null;
            if( index2 == -1) return tmp.substring(index1 + 1, index3).trim();
            else return tmp.substring(index1 + 1, index2).trim();
        }
        return null;
    }

    public String getEndedTime( Element el) {

        if( el != null){
            String tmp = el.text();

            int index1 = tmp.indexOf('(');
            int index2 = tmp.indexOf(8211);
            int index3 = tmp.indexOf(')');
            if( index1 == -1) return null;
            if( index3 == -1) return null;
            if( index2 == -1) return null;
            else return tmp.substring(index2 + 1, index3).trim();
        }
        return null;
    }

    public JSONArray getKings(Document document) {
        JSONArray arr = new JSONArray();
        Elements infoBox = document.select(".infobox");
        if( infoBox.size() == 0){
            return null;
        }else{
            Elements list = infoBox.get(0).select("tr.mergedrow:not(:has(td[style='padding-left:0em;text-align:left;']))>td:not(:has(sup))>a[title~=[^0-9]]:not(:has(sup))");
            for( int i = 0; i < list.size(); ++i){
                arr.add( list.get(i).text());
            }

            return arr;
        }
    }

    public String getBrief(Document document) {
        Element content = document.select("#mw-content-text>.mw-parser-output>p:has(a)").first();
        return content.text();
    }
}
