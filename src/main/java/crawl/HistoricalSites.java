package crawl;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.JsonHandler;

public class HistoricalSites {

	private final static String fileData = "data/HistoricalSites.json";
	private String name;
	private String tongQuan;
	private String diaDiem;
	private String nhanVat;
	
	JSONArray leHoi = new JSONArray();

	public void getData() throws IOException {
		String url = "https://nguoikesu.com/di-tich-lich-su";
		String url1 = "https://nguoikesu.com/di-tich-lich-su?types[0]=1&start=10";
		Document doc = Jsoup.connect(url).get();
		Elements getByClass = doc.getElementsByClass("list-group-item list-group-item-action");
		Elements getA = getByClass.select("a");
		Elements getP = doc.select("span.tag-body p");
		
		int j = 0;
		int id = 0;
		JSONArray arr = new JSONArray();
		for (int i = 0; i < getA.size(); i = i + 2) {
			String urlSau = "https://nguoikesu.com" + getA.get(i).attr("href");// truy cap link con
			name = getA.get(i).text();
			if(j == 1) j++;
			tongQuan = getP.get(j).text();
			j++;
			
			Document doc1 = Jsoup.connect(urlSau).get();
			
			try {
			Elements elms = doc1.getElementsByClass("infobox");
			// chon the tr co th th chua "Dia chi" trong the tfbody
			Elements elm = doc1.select("table.infobox tbody tr:has(th:contains(Địa chỉ)),"
					+ "table.infobox tbody tr:has(th:contains(Địa điểm))"); 
			diaDiem = elm.get(0).text();
			} catch (Exception e) {
				try {
				Elements elm = doc1.select("table.infobox tbody tr:has(th:contains(Vị trí))"); 
				diaDiem = elm.get(0).text();
				}catch(Exception e1) {
					diaDiem = "Khong tim thay!";
				}
			}
			
			Elements lehoi = doc1.select("li:contains(Lễ hội):not(:contains(^))");
			int num = 0;
			for(Element e : lehoi) {
				leHoi.add(e.text());
				num++;
			}
			if(leHoi.isEmpty()) {
				leHoi.add("Khong co thong tin!");
			}
			
			int k = 0;
			JSONObject obj = new JSONObject();
			obj.put("ID", "Site" + id);
			id++;
			obj.put("Dia Diem", diaDiem);
			obj.put("Name", name);
			obj.put("Tong Quan", tongQuan);
			obj.put("Nhan Vat", nhanVat);
			obj.put("Le Hoi", leHoi);
			arr.add(obj);
			leHoi = new JSONArray();
		}
		JsonHandler.writeJsonFile(arr, fileData);
	}

	public String getName() {
		return name;
	}

	public String getTongQuan() {
		return tongQuan;
	}

	public String getDiaDiem() {
		return diaDiem;
	}

	public String getNhanVat() {
		return nhanVat;
	}

	public JSONArray getLeHoi() {
		return leHoi;
	}

	public static void main(String[] args) throws IOException {
		HistoricalSites cd = new HistoricalSites();
		cd.getData();
	}
}
