import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class Main {
    //    static String summary;
    static String getTime(Element element) {
        return element.select("td").text();
    }
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#C%C3%A1c_l%E1%BB%85_h%E1%BB%99i_l%C3%A0_di_s%E1%BA%A3n_v%C4%83n_h%C3%B3a_qu%E1%BB%91c_gia").get();
        Elements table = document.getElementsByClass("prettytable wikitable");
        Elements tds = Objects.requireNonNull(table.first()).getElementsByTag("tr");
//        for(int j = 1; j<tds.size(); j++)
//        {
//            System.out.print("j = "+j+" ");
//            System.out.println(tds.get(j).text());
            Elements d = tds.get(1).select("td");
            for (int i= 0; i< d.size(); i++){
                System.out.print("i = "+i+" ");
                System.out.println(d.get(i).text());
            }
//        }
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