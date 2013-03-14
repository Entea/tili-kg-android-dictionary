package kg.tili.async;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import kg.tili.R;
import kg.tili.SingleWordActivity;
import kg.tili.WordListActivity;
import kg.tili.api.TiliApi;
import kg.tili.utils.DialogUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: entea
 * Date: 3/14/13
 * Time: 3:50 PM
 */
public class WordLoader extends AsyncTask<String, String, List<String>> {

    private final WordListActivity context;
    private JSONArray translations;
    private ProgressDialog dialog;

    public WordLoader(WordListActivity activity) {
        context = activity;
    }

    @Override
    protected void onPreExecute() {
        dialog = DialogUtils.getProgressDialog(context);
        dialog.show();
    }

    @Override
    protected List<String> doInBackground(String... strings) {
        try {
            TiliApi api = new TiliApi();
            translations = api.searchKeyword(strings[0]);
            if (translations.length() == 0) {
                return Collections.emptyList();
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
            return keywords;

        } catch (JSONException e) {
            publishProgress("Неправильный формат данных. Пожалуйста сообщите разработчикам");
        } catch (IOException e) {
            publishProgress("Нет связи с Tili. Проверьте ваше интернет соединение");
        }
        return Collections.emptyList();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        dialog.dismiss();
        Toast.makeText(context, values[0], Toast.LENGTH_LONG).show();
        context.finish();
    }

    @Override
    protected void onPostExecute(final List<String> strings) {
        dialog.dismiss();

        if (strings.size() == 0) {
            Toast.makeText(context, "Перевод не найден", Toast.LENGTH_SHORT).show();
            context.finish();
            return;
        }

        context.setListAdapter(new ArrayAdapter<String>(context, R.layout.list_item, strings));
        context.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Intent intent = new Intent(context, SingleWordActivity.class);
                    JSONObject selectedTranslation = translations.getJSONObject(i);
                    intent.putExtra("word", selectedTranslation.getString("keyword"));
                    intent.putExtra("dictname", selectedTranslation.getString("dictname"));
                    intent.putExtra("value", selectedTranslation.getString("value"));
                    context.startActivity(intent);
                } catch (JSONException e) {
                    Toast.makeText(context, "Неверный формат данных", Toast.LENGTH_SHORT).show();
                    Log.e(getClass().getName(), "Error parsing json", e);
                }
            }
        });
    }
}
