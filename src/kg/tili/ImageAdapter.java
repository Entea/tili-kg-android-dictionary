package kg.tili;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import kg.tili.data.GlossaryItem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GlossaryItem> glossaryItems = new ArrayList<GlossaryItem>();

    public ImageAdapter(Context c, ArrayList<GlossaryItem> imageUriList) {
        mContext = c;
        glossaryItems = imageUriList;
    }

    public int getCount() {
        return glossaryItems.size();
    }

    public Object getItem(int position) {
        return glossaryItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            v = li.inflate(R.layout.glossary_item, null);
        } else {
            v = convertView;
        }
        TextView tv = (TextView) v.findViewById(R.id.glossary_item_text);
        tv.setText(glossaryItems.get(position).getText());
        ImageView iv = (ImageView) v.findViewById(R.id.glossary_item_image);
        iv.setImageBitmap(getImageUri(position));
        return v;
    }

    public Bitmap getImageUri(int position) {
        return getImageBitmap(glossaryItems.get(position).getImageUrl());  //To change body of created methods use File | Settings | File Templates.
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("GetImageBitmap Error", "Error getting bitmap", e);
        }
        return bm;
    }
}