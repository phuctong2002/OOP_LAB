package crawler.hitoricalsite;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.Crawler;
import util.JsonHandler;

public class SiteNKS extends Crawler implements ISite {

	JSONArray mang = new JSONArray();
	private final static String fileData = "/data/HistoricalSites.json";
	private String name;
	private String tongQuan;
	private String diaDiem;
	private String dienTich;

	JSONArray leHoi = new JSONArray();

	public SiteNKS() {
		setData(JsonHandler.readJsonFile("HistoricalSites.json"));
		mang = getData();
	}

	@Override
	public void get() {
//		String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
		String url = "https://nguoikesu.com/di-tich-lich-su";

		try {
			Document doc = Jsoup.connect(url).get();
//			Elements com = doc.select("div.wikitable sortable jquery-tablesorter tbody tr td:first-child");
			Elements getByClass = doc.getElementsByClass("list-group-item list-group-item-action");
			Elements com = getByClass.select("a");
			
			Elements getP = new Elements();
			Elements spanElements = doc.getElementsByClass("tag-body");
			for (Element span : spanElements) {
				Element firstPElement = span.selectFirst("p");
				getP.add(firstPElement);
			}

			int j = 0;
			for (int i = 0; i < com.size(); i = i + 2) {
				
				String urlSau = "https://nguoikesu.com" + com.get(i).attr("href");// truy cap link con
				name = com.get(i).text();
				JSONObject tmp = findObject(name);
				System.out.println(name);
				if (tmp == null) {
					// khong ton tai
					tongQuan = getP.get(j).text();
					j++;

					Document doc1 = Jsoup.connect(urlSau).get();
					
					diaDiem = getDiaDiem(doc1);
					leHoi = getLeHoi(doc1);
					
					JSONObject obj = new JSONObject();
					obj.put("Địa điểm", diaDiem);
					obj.put("tên", name);
					obj.put("Tổng quan", tongQuan);
					obj.put("Diện tích", null);
					obj.put("Lễ hội", leHoi);
					mang.add(obj);
					leHoi = new JSONArray();
				} else {
					// ton tai
					System.out.println("Exsisted");
					j++;
					continue;
				}
			}
			JsonHandler.writeJsonFile(mang, fileData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName(Document doc) {
		return null;
	}

	@Override
	public String getTongQuan(Document doc) {
		return null;
	}

	@Override
	public String getDienTich(Document doc) {
		return null;
	}

	@Override
	public String getDiaDiem(Document doc) {
		String diaDiem;
		try {
			Elements elms = doc.getElementsByClass("infobox");
			// chon the tr co th th chua "Dia chi" trong the tfbody
			Elements elm = doc.select("table.infobox tbody tr:has(th:contains(Địa chỉ)),"
					+ "table.infobox tbody tr:has(th:contains(Địa điểm))");
			diaDiem = elm.get(0).text();
		} catch (Exception e) {
			try {
				Elements elm = doc.select("table.infobox tbody tr:has(th:contains(Vị trí))");
				diaDiem = elm.get(0).text();
			} catch (Exception e1) {
				diaDiem = null;
			}
		}
		return diaDiem;
	}

	@Override
	public JSONArray getLeHoi(Document doc) {
		JSONArray fesList = new JSONArray();
		Elements fes = doc.select("li:contains(Lễ hội):not(:contains(^))");
		int num = 0;
		for (Element e : fes) {
			fesList.add(e.text());
			num++;
		}
		if (fesList.isEmpty()) {
			fesList = null;
		}
		return fesList;
	}

	public static void main(String[] args) {
		SiteNKS sw = new SiteNKS();
		sw.get();
	}
}
