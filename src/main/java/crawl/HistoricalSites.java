package crawl;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import chitiet.CacDiaDiem;
import chitiet.DiaDiemDiTich;
import chitiet.HoaLu;
import chitiet.ThanhThangLong;
import util.JsonHandler;

public class HistoricalSites {

	private final static String fileData = "data/HistoricalSites.json";

	public static void main(String[] args) throws IOException {
		CacDiaDiem cacDiaDiem = new CacDiaDiem();

		HoaLu hL = new HoaLu();
		hL.getData();
		DiaDiemDiTich hoaLu = new DiaDiemDiTich(hL.getName(), hL.getDiaDiem(), hL.getTongQuan(),
				hL.getNhanVat(), hL.getLeHoi());
		
		ThanhThangLong tTL = new ThanhThangLong();
		tTL.getData();
		DiaDiemDiTich thanhThangLong = new DiaDiemDiTich(tTL.getName(), tTL.getDiaDiem(), tTL.getTongQuan(),
				tTL.getNhanVat(), tTL.getLeHoi());
		
		cacDiaDiem.addDiaDiem(hoaLu);
		cacDiaDiem.addDiaDiem(thanhThangLong);

		JSONArray arr = new JSONArray();
		for (int i = 0; i < cacDiaDiem.getListDiaDiem().size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", "Site" + i);
			obj.put("name", cacDiaDiem.getListDiaDiem().get(i).getName());
			obj.put("Tong Quan", cacDiaDiem.getListDiaDiem().get(i).getTongQuan());
			obj.put("Nhan Vat", cacDiaDiem.getListDiaDiem().get(i).getNhanVat());
			obj.put("Le Hoi", cacDiaDiem.getListDiaDiem().get(i).getLeHoi());
			System.out.println(obj);
			arr.add(obj);
		}
//		JsonHandler.writeJsonFile(arr, fileData);
	}
}
