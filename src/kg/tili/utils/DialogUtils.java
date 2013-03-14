package kg.tili.utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;
import kg.tili.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: entea
 * Date: 2/4/13
 * Time: 7:13 PM
 */
public class DialogUtils {
    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);

        dialog.setMessage(context.getString(R.string.loading_data));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);

        return dialog;
    }
}
