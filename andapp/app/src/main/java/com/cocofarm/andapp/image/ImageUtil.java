package com.cocofarm.andapp.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.cocofarm.andapp.conn.ImageConn;

import java.io.InputStream;

public class ImageUtil {
    public static void load(ImageView imageView, String filename) {
        ImageConn conn = new ImageConn(null, filename);
        conn.onExcute((isResult, data) -> {
            if (data != null) {
                InputStream inputStream = data.byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
