package kg.tili;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * User: entea
 * Date: 4/18/12
 * Time: 1:31 PM
 */
public class SingleWordActivity extends Activity {

    private TextView wordText;
    private static final String TAG = SingleWordActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_word);

        wordText = (TextView) findViewById(R.id.display_word);
        TextView keyword = (TextView) findViewById(R.id.display_keyword);
        TextView dictnameView = (TextView) findViewById(R.id.display_dict_name);

        Bundle extras = getIntent().getExtras();
        String word = extras.getString("word");
        String dictname = extras.getString("dictname");
        String value = extras.getString("value");

        Log.i(TAG, "Showing word: " + word);
        wordText.setText(value);
        keyword.setText(word);
        dictnameView.setText(dictname);
        wordText.setMovementMethod(new ScrollingMovementMethod());
    }
}