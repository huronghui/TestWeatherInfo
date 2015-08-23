package network.json;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;

import org.json.JSONObject;

/**
 * Created by hrh on 2015/8/21.
 */
public class PackageParser {
    public static final String TAG = PackageParser.class.getSimpleName();

    public <T> T parse(JSONObject jSONObject, java.lang.Class<T> clazz) {
        Gson gson = new Gson();
        T bean = gson.fromJson(jSONObject.toString(), clazz);
        return bean;
    }
}
