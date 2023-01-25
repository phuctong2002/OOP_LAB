package service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.JsonHandler;

public class Search {
    public JSONArray search(String name, String fileName){
        name = name.toLowerCase();
        JSONArray arr = JsonHandler.readJsonFile( fileName);
        JSONArray result = new JSONArray();
        if( name.length() == 0) return result;
        for( int i = 0; i < arr.size(); ++i){
            JSONObject obj = (JSONObject) arr.get(i);
            String tmp = ((String)obj.get("tên")).toLowerCase();
            if( tmp.contains( name)){
                result.add( arr.get(i));
            }
        }
        return result;
    }
}
