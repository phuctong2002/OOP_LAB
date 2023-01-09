package util;

import org.json.simple.JSONArray;

import java.io.FileWriter;

public class JsonHandler {


    public static void writeJsonFile(JSONArray obj, String file){
        try{
            FileWriter f = new FileWriter( "./src/main/java/" + file);
            f.write( obj.toJSONString());
            f.close();
        }catch(Exception e){
            System.out.println("Error in write file");
        }
    }


    public static Object readJsonFile( String file){
        return null;
    }
}
