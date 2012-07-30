package kg.tili;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Dictionary extends Activity {
    private static final String TAG = Dictionary.class.getName();
    Button searchButton;
    Button buttonO;
    Button buttonU;
    Button buttonN;
    EditText word;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        word = (EditText) findViewById(R.id.search);

        buttonO = (Button) findViewById(R.id.button_o);
        buttonO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                word.getText().append("ө");
            }
        });
        buttonU = (Button) findViewById(R.id.button_u);
        buttonU.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                word.getText().append("ү");
            }
        });
        buttonN = (Button) findViewById(R.id.button_n);
        buttonN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                word.getText().append("ң");
            }
        });

        Button dictButton = (Button) findViewById(R.id.dictionary_button);
        dictButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse("http://tili.kg/dict/dlist/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        searchButton = (Button) findViewById(R.id.translatebutton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Dictionary.this, WordListActivity.class);
                EditText input = (EditText) findViewById(R.id.search);
                if (input.getText().toString().trim().length() < 1) {
                    Toast.makeText(Dictionary.this, "Введите слово для перевода", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("word", input.getText().toString().trim());
                startActivity(intent);
            }
        });

        Button glossaryButton = (Button) findViewById(R.id.glossary_button);
        glossaryButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(Dictionary.this, GlossaryActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
