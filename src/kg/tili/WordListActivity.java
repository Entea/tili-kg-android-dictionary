package kg.tili;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import kg.tili.api.TiliApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: entea
 * Date: 4/18/12
 * Time: 11:44 AM
 */
public class WordListActivity extends ListActivity {
    String[] words;
    JSONArray translations;
    private static final String TAG = WordListActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(WordListActivity.this, SingleWordActivity.class);
                    JSONObject selectedTranslation = translations.getJSONObject(i);
                    intent.putExtra("word", selectedTranslation.getString("keyword"));
                    intent.putExtra("dictname", selectedTranslation.getString("dictname"));
                    intent.putExtra("value", selectedTranslation.getString("value"));
                    startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(WordListActivity.this, "Неверный формат данных", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error parsing json", e);
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        String word = extras.getString("word");
        try {
            TiliApi api = new TiliApi();
            translations = api.searchKeyword(word);
            if (translations.length() == 0) {
                Toast.makeText(WordListActivity.this, "Перевод не найден", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            ArrayList<String> keywords = new ArrayList<String>();
            for (int i = 0; i < translations.length(); i++) {
                JSONObject translatedEntity = translations.getJSONObject(i);
                String value = translatedEntity.getString("value");
                if (value.length() > 70) {
                    value = value.substring(0, 70) + " ...\n >>>";
                }
                keywords.add(translatedEntity.getString("keyword") + " \n" + value);
            }
            words = keywords.toArray(new String[keywords.size()]);

            setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, words));
        } catch (JSONException e) {
            Toast.makeText(this, "Неправильный формат данных. Пожалуйста сообщите разработчикам", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Нет связи с Tili. Проверьте ваше интернет соединение", Toast.LENGTH_LONG).show();
        }

    }
}
