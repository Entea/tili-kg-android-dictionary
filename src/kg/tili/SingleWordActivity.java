package kg.tili;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * User: entea
 * Date: 4/18/12
 * Time: 1:31 PM
 */
public class SingleWordActivity extends SherlockActivity {
    private static final String TAG = SingleWordActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_word);

        TextView wordTextView = (TextView) findViewById(R.id.display_word);
        TextView keywordView = (TextView) findViewById(R.id.display_keyword);
        TextView dictnameView = (TextView) findViewById(R.id.display_dict_name);

        Bundle extras = getIntent().getExtras();
        String word = extras.getString("word");
        String dictname = extras.getString("dictname");
        String value = extras.getString("value");

        setTitle(word);
        getSupportActionBar().setSubtitle("Перевод слова");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.i(TAG, "Showing word: " + word);

        wordTextView.setText(value);
        keywordView.setText(word);
        dictnameView.setText(dictname);
        wordTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Intent intent = new Intent(this, Dictionary.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}