import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Main {
    //    static String summary;
    static String getTime(Element element) {
        return element.select("td").text();
    }
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#C%C3%A1c_l%E1%BB%85_h%E1%BB%99i_l%C3%A0_di_s%E1%BA%A3n_v%C4%83n_h%C3%B3a_qu%E1%BB%91c_gia").get();
        Elements table = document.getElementsByClass("mw-parser-output");
        Elements tds = Objects.requireNonNull(table.first()).getElementsByTag("ul");
        Elements d = tds.get(10).select("li");
//        for(int j = 1; j<tds.size(); j++)
//        {
//
//        System.out.println("j ="+j+" "+tds.get(j).text());
//        }
//        System.out.println(d.get(1).select("li").text());
        //tach dia diem, le hoi
        List<String> list = new ArrayList<>();
        String a = d.get(1).select("li").text();
        Collections.addAll(list, a.split(": ", 0));
        System.out.println(list.get(1));
        //tach le hoi
        List<String> listFes = new ArrayList<>();
        Collections.addAll(listFes, list.get(1).split(", ", 0));
        System.out.println(listFes);
       String str = listFes.get(0).replaceAll("[^0-9/-]", " ");
       // tach ngay
        List<String> m = new ArrayList<>();
        Collections.addAll(m, str.split(" ", 0));
        System.out.println(m.get(m.size()-1));
        String input = "Hi, X How-how; (are:)any 123 am lich den 12 am lich you?";
        String tmp = input.replaceAll("\\(.*?\\)","");
        System.out.println(tmp);
        String[] parts = tmp.split("[,\\;]");
        System.out.println(Arrays.toString(parts));
        String [] parts2 = parts[2].split( "(?<=\\D)(?=\\d)");
        System.out.println(Arrays.toString(parts2));
//        Map<String, String> map = new HashMap<>();
//        map.put("A","B");
//        map.put("C", "D");
//        System.out.println(map.get("A"));
//        System.out.println("A"+);
//        Pair<String , String>pair = new Pair<>(null, null);
//        System.out.println(d.get(0).text());
//        String k = Arrays.toString(str.split(" ", 0));
//       System.out.println(k);
//        String time = getTime(tds.get(49));
//        System.out.println(tds.get(1).select("td"));
//        Elements e = tds.get(4).select("a");
//        String url = e.attr("href");
//        Document doc = Jsoup.connect("https://vi.wikipedia.org" + url).get();
//        Element p = doc.select(".mw-parser-output > p").first();
//        StringBuilder summary  = new StringBuilder();
//        while (p.tagName().equals("p")) {
//            summary.append(p.text());
//            p = p.nextElementSibling();
//        }
//        System.out.println(summary);
//    }
}}