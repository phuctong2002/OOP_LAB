package util;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class JsonHandler {
    public static void writeJsonFile(JSONArray obj, String fileName){
        try{
            FileWriter f = new FileWriter( "./src/main/resources" + fileName);
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
            Object obj = parser.parse( new FileReader("./src/main/resources" + fileName));
            return (JSONArray) obj;
        }catch ( Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
