package test;

import crawl.HistoricEventCrawl;

public class MainTest {
     public static void main(String[] args){

          HistoricEventCrawl obj = new HistoricEventCrawl( "url/HistoricEvent.txt");
          obj.getData();
     }
}
