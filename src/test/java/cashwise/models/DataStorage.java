package cashwise.models;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {

    private Map<String, String> data = new HashMap<>();

    public void addData(String key, String value){
        data.put(key, value);
    }

    public String get(String key){
        return data.get(key);
    }




}
