package test;

import crawl.DynastyCrawler;
import crawl.HistoricalFigureCrawler;
import org.json.simple.JSONArray;
import util.JsonHandler;

public class MainTest {
     public static void main(String[] args){
//          JSONArray arr = new JSONArray();
//          arr = JsonHandler.readJsonFile("data/DynastyData.json");
//          System.out.print( arr);

          HistoricalFigureCrawler obj = new HistoricalFigureCrawler();
          obj.getData();
     }

}
