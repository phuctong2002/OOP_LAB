package test;

import crawl.CulturalFestivalCrawl;
import crawl.DynastyCrawl;

public class MainTest {
     public static void main(String[] args){
//          DynastyCrawl obj = new DynastyCrawl( "url/DynastyURL.txt");
//          obj.getData();
          CulturalFestivalCrawl obj = new CulturalFestivalCrawl( "url/CulturalFestival.txt");
          obj.getData();
     }
}
