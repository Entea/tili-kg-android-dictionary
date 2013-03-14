package kg.tili.async;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import kg.tili.GlossaryActivity;
import kg.tili.adapter.ImageAdapter;
import kg.tili.R;
import kg.tili.SingleWordActivity;
import kg.tili.api.TiliApi;
import kg.tili.data.GlossaryItem;
import kg.tili.utils.DialogUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: entea
 * Date: 3/14/13
 * Time: 4:15 PM
 */
public class GlossaryLoader extends AsyncTask<Integer, String, List<GlossaryItem>> {

    private final GlossaryActivity context;
    private ProgressDialog dialog;

    public GlossaryLoader(GlossaryActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = DialogUtils.getProgressDialog(context);
        dialog.show();
    }

    @Override
    protected List<GlossaryItem> doInBackground(Integer... integers) {
        try {
            TiliApi tiliApi = new TiliApi();
            return tiliApi.getGlossary(integers[0]);
        } catch (Exception e) {
            Log.e("Glossary", "Error occured", e);
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
    protected void onPostExecute(List<GlossaryItem> glossaryItems) {
        dialog.dismiss();

        GridView gridview = (GridView) context.findViewById(R.id.glossary_view);

        gridview.setAdapter(new ImageAdapter(context, glossaryItems));

        final List<GlossaryItem> finalGlossaryItems = glossaryItems;

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                GlossaryItem item = finalGlossaryItems.get(position);
                if (item.isTag()) {
                    Intent intent = new Intent(context, GlossaryActivity.class);
                    intent.putExtra("id", item.getId());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SingleWordActivity.class);
                    intent.putExtra("word", item.getText());
                    intent.putExtra("dictname", item.getDictname());
                    intent.putExtra("value", item.getValue());
                    context.startActivity(intent);
                }
            }
        });
    }
}
