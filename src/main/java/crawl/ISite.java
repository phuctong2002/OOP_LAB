package crawler.hitoricalsite;

import org.json.simple.JSONArray;
import org.jsoup.nodes.Document;

public interface ISite {
	public String getName(Document doc);
	public String getTongQuan(Document doc);
	public String getDienTich(Document doc);
	public String getDiaDiem(Document doc);
	public JSONArray getLeHoi(Document doc);
}
