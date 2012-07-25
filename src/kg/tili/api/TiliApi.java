package kg.tili.api;

import android.net.Uri;
import android.util.Log;
import kg.tili.data.GlossaryItem;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * User: entea
 * Date: 4/18/12
 * Time: 1:35 PM
 */
public class TiliApi {
    public static final String TAG = TiliApi.class.getName();

    public JSONArray search(String word) throws IOException, JSONException {
        URL url = new URL("http://tili.kg/dict/api/search/" + Uri.encode(word));
        return parseJson(url);
    }

    private JSONArray parseJson(URL url) throws IOException, JSONException {
        Log.i(TAG, "Opening input stream for " + url);
        InputStream inputStream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String json = "";
        while ((line = reader.readLine()) != null) {
            json += line;
        }

        Log.i(TAG, "Parsing json");
        return new JSONArray(json);
    }

    public ArrayList<GlossaryItem> getGlossary(int id) throws IOException, JSONException {
        URL url = new URL("http://kt.dev/dict/api/glosssary/" + id);
        JSONArray jsonArray = parseJson(url);

        ArrayList<GlossaryItem> items = new ArrayList<GlossaryItem>();
        GlossaryItem item = new GlossaryItem();
        item.setImageUrl("http://tili.kg/dict/assets/images/tag/1.jpg");
        item.setText("Человек");
        item.setId(1);
        items.add(item);
        return items;
    }
}
