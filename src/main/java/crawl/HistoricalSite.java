package crawler;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.JsonHandler;

public class HistoricalSite {
	private final static String fileData = "/data/HistoricalSites.json";
	private String name;
	private String tongQuan;
	private String diaDiem;
	private String dienTich;

	JSONArray leHoi = new JSONArray();

	public void getData() throws IOException {

		String base = "https://nguoikesu.com/dia-danh";
		int index = 0;
		JSONArray arr = new JSONArray();
		while (index <= 765) {
			String url = base + "?start=" + index;

			Document doc = Jsoup.connect(url).get();

			Elements getA = doc.select("div.item-content > div.page-header h2 a[href]");

			Elements getP = new Elements();
			Elements spanElements = doc.getElementsByClass("item-content");
			for (Element span : spanElements) {
				Element firstPElement = span.selectFirst("p");
				getP.add(firstPElement);
			}

			int j = 0; // phan tu tong quan

			for (int i = 0; i < getA.size(); i = i + 1) {
				try {
					// Lay ten va tong quan
					String urlSau = "https://nguoikesu.com" + getA.get(i).attr("href");// truy cap link con
					Document doc1 = Jsoup.connect(urlSau).get();
					if(doc1.getElementsByClass("infobox").size() == 0) {
						j++;
						continue;
					}
					String s = getA.get(i).text();
					if(s.contains("nước")) {
						j++;
						continue;
					}
					name = s;
					System.out.println("ten: " + name);
					tongQuan = getP.get(j).text();
					j++;

					
					// Lay "Dia chi"
					try {
						// chon the tr co th th chua "Dia chi" trong the tfbody
						Elements getDiaChi = doc1.select("table.infobox tbody tr:has(th:matchesOwn(^Địa chỉ$)),"
								+ "table.infobox tbody tr:has(th:matchesOwn(^Địa điểm$)),"
								+ "table.infobox tbody tr:has(th:matchesOwn(^Khu vực$)),"
								+ "table.infobox tbody tr:has(th:matchesOwn(^Tọa độ$))");
						diaDiem = getDiaChi.get(0).text();
					} catch (Exception e) {
						try {
							Elements getDiaChi = doc1.select("table.infobox tbody tr:has(th:matchesOwn(^Vị trí$))");
							diaDiem = getDiaChi.get(0).text();
						}catch (Exception e1) {
							// TODO: handle exception
							diaDiem = null;
						}
					}
					System.out.println("dia diem: " + diaDiem);
					// Lay dien tich
					try {
						Elements getDienTich = doc1.select("table.infobox tbody tr:has(th:matchesOwn(^Diện tích$)) td,"
								+ "div.infobox tbody tr:has(th:matchesOwn(^Diện tích bề mặt$)) td");
						dienTich = getDienTich.get(0).text();
					}catch (Exception e) {
						dienTich = null;
					}
					System.out.println("dien tich: " + dienTich);
					// lay le hoi
					try {
						Elements lehoi = doc1.select("li:contains(Lễ hội):not(:contains(^))");
						int num = 0;
						for (Element e : lehoi) {
							leHoi.add(e.text());
							num++;
						}
						if (leHoi.isEmpty()) {
							leHoi = null;
						}
					}catch(Exception e) {
						leHoi = null;
					}
					System.out.println("le hoi: " + leHoi);
				} catch (Exception e) {
					continue;
				}
				// Luu data vao file json
				JSONObject obj = new JSONObject();
				obj.put("Địa điểm", diaDiem);
				obj.put("tên", name);
				obj.put("Tổng quan", tongQuan);
				obj.put("Diện tích", dienTich);
				obj.put("Lễ hội", leHoi);
				arr.add(obj);
				leHoi = new JSONArray();
			}

			index += 5;
		}
		JsonHandler.writeJsonFile(arr, fileData);

	}
	public static void main(String[] args) throws IOException {
		HistoricalSite cd = new HistoricalSite();
		cd.getData();
	}
}
