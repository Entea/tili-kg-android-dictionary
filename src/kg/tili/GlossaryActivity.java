package kg.tili;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import kg.tili.api.TiliApi;
import kg.tili.data.GlossaryItem;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: entea
 * Date: 7/24/12
 * Time: 11:51 AM
 */
public class GlossaryActivity extends ListActivity {

    public static final String TAG = "Glossary";
    private ArrayList<GlossaryItem> glossaryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int id = getIntent().getExtras().getInt("id", 0);
        TiliApi api = new TiliApi();
        try {
            glossaryItems = api.getGlossary(id);
        } catch (IOException e) {
            Log.e(TAG, "", e);
            Toast.makeText(this, "Ошибка чтения данных", Toast.LENGTH_SHORT);
        } catch (JSONException e) {
            Log.e(TAG, "", e);
            Toast.makeText(this, "Ошибка обработки данных", Toast.LENGTH_SHORT);
        }
        setListAdapter(new ArrayAdapter<GlossaryItem>(this, R.layout.glossary_item, glossaryItems));
    }
}
