package test;

import crawl.DynastyCrawl;

public class MainTest {
     public static void main(String[] args){
          DynastyCrawl obj = new DynastyCrawl( "url/DynastyURL.txt");
          obj.getData();
     }
}
