package crawler;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public abstract class Crawler {
	private JSONArray data;
    public JSONObject findObject(String name){
        for( int i = 0; i < data.size(); ++i){
            JSONObject obj = (JSONObject) data.get(i);
            if( ((String) obj.get("tên")).toLowerCase().equals( name.toLowerCase())){
                return obj;
            }
        }
        return null;
    }
    public boolean checkField( String field, JSONObject obj){
        if( obj.get( field) == null) return false;
        return true;
    }
    public abstract void get();

    public void setData(JSONArray data) {
        this.data = data;
    }

    public JSONArray getData() {
        return data;
    }
}
