package kg.tili;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import kg.tili.api.TiliApi;
import kg.tili.data.GlossaryItem;

import java.util.ArrayList;

public class GlossaryActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int id = 0;
        if (extras != null) {
            id = extras.getInt("id", 0);
        }

        setContentView(R.layout.glossary_list);
        GridView gridview = (GridView) findViewById(R.id.glossary_view);

        TiliApi tiliApi = new TiliApi();
        ArrayList<GlossaryItem> glossaryItems = new ArrayList<GlossaryItem>();
        try {
            glossaryItems = tiliApi.getGlossary(id);
        } catch (Exception e) {
            Log.e("Glossary", "Error occured", e);
            Toast.makeText(this, "Нет связи с Tili. Проверьте ваше интернет соединение", Toast.LENGTH_LONG).show();
        }

        gridview.setAdapter(new ImageAdapter(this, glossaryItems));
        setProgressBarIndeterminateVisibility(false);

        final ArrayList<GlossaryItem> finalGlossaryItems = glossaryItems;
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlossaryItem item = finalGlossaryItems.get(position);
                if (item.isTag()) {
                    Intent intent = new Intent(GlossaryActivity.this, GlossaryActivity.class);
                    intent.putExtra("id", item.getId());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(GlossaryActivity.this, SingleWordActivity.class);
                    intent.putExtra("word", item.getText());
                    intent.putExtra("dictname", item.getDictname());
                    intent.putExtra("value", item.getValue());
                    startActivity(intent);
                }
            }
        });
    }
}
