package com.cocofarm.andapp.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.cocofarm.andapp.conn.ImageConn;

import java.io.InputStream;

public class ImageUtil {
    private static Bitmap bitmap = null;

    public static Bitmap load(String filename) {
        ImageConn conn = new ImageConn(null, filename);
        conn.onExcute((isResult, data) -> {
            if (data != null) {
                bitmap = BitmapFactory.decodeStream(data.byteStream());
            }
        });
        return bitmap;
    }
}
