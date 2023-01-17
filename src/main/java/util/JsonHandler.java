package util;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

public class JsonHandler {

    // write data to json file
    public static void writeJsonFile(JSONArray obj, String fileName){
        try{
            FileWriter f = new FileWriter( "./src/main/java/" + fileName);
            f.write( obj.toJSONString());
            f.close();
        }catch(Exception e){
            System.out.println("Error in write file");
        }
    }

    // read data from json file
    public static JSONArray readJsonFile( String fileName){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse( new FileReader("./src/main/java/" + fileName));
            return (JSONArray) obj;
        }catch ( Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
