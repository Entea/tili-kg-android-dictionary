package kg.tili.api;

import android.net.Uri;
import android.util.Log;
import kg.tili.data.GlossaryItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONArray searchKeyword(String word) throws IOException, JSONException {
        URL url = new URL("http://tili.kg/dict/api/search/" + Uri.encode(word));
        return parseJson(url);
    }

    private JSONArray parseJson(URL url) throws IOException, JSONException {
        String json = readJson(url);
        return new JSONArray(json);
    }

    private String readJson(URL url) throws IOException {
        Log.i(TAG, "Opening input stream for " + url);
        InputStream inputStream = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String json = "";
        while ((line = reader.readLine()) != null) {
            json += line;
        }

        Log.i(TAG, "Parsing json");
        return json;
    }

    private JSONObject parseJsonObject(URL url) throws IOException, JSONException {
        String json = readJson(url);
        return new JSONObject(json);
    }


    public ArrayList<GlossaryItem> getGlossary(int id) throws IOException, JSONException {
        URL url = new URL("http://tili.kg/dict/api/glossary/" + id);
        JSONObject jsonObject = parseJsonObject(url);
        JSONArray tags = jsonObject.getJSONArray("tags");

        ArrayList<GlossaryItem> items = new ArrayList<GlossaryItem>();
        if (tags.length() > 0) {
            for (int i = 0; i < tags.length(); i++) {
                JSONObject tag = tags.getJSONObject(i);
                GlossaryItem item = new GlossaryItem();
                item.setImageUrl("http://tili.kg/dict/assets/images/tag/" + tag.getInt("id") + ".jpg");
                item.setText(tag.getString("tag"));
                item.setId(tag.getInt("id"));
                items.add(item);
            }
        } else {
            JSONArray pics = jsonObject.getJSONArray("pics");
            JSONArray words = jsonObject.getJSONArray("words");
            for (int j = 0; j < words.length(); j++) {
                JSONObject word = words.getJSONObject(j);
                GlossaryItem item = new GlossaryItem();
                item.setText(word.getString("keyword"));
                item.setTag(false);
                item.setDictname(word.getString("dictname"));
                item.setValue(word.getString("value"));

                for (int i = 0; i < pics.length(); i++) {
                    JSONObject pic = pics.getJSONObject(i);
                    if (pic.getString("word").equals(item.getText())) {
                        item.setImageUrl(pic.getString("thumbnail"));
                        break;
                    }
                }

                if (item.getImageUrl() == null) {
                    item.setImageUrl("http://tili.kg/dict/assets/images/noimage.gif");
                }
                items.add(item);
            }
        }

        Log.i(getClass().getName(), "Fetched glossary items: " + items.size());


        return items;
    }
}
