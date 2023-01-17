package test;

import crawl.DynastyCrawler;
import crawl.HistoricalFigureCrawler;

public class MainTest {
     public static void main(String[] args){
//          DynastyCrawler obj = new DynastyCrawler("url/DynastyURL.txt");
//          obj.getData();


          HistoricalFigureCrawler obj = new HistoricalFigureCrawler("url/HistoricalFigure.txt");
          obj.getData();
     }

}
