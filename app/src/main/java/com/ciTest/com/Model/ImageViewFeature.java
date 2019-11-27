package com.ciTest.com.Model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciTest.com.Activity.ShowPhotoActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageViewFeature {


    private static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }


    public static void CreateImageView(final int imageIndex, final ImageView image1, final ImageView image2, final ImageView image3, final ImageView image4, String url) {

        new  AsyncTask<String, Void, Bitmap>() {


            @Override
            protected Bitmap doInBackground(String... params) {

                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result) {

                if (imageIndex == 0) {
                    image1.setImageBitmap(result);
                } else if (imageIndex == 1) {
                    image2.setImageBitmap(result);
                } else if (imageIndex == 2) {
                    image3.setImageBitmap(result);
                } else if (imageIndex == 3) {
                    image4.setImageBitmap(result);
                }
                super.onPostExecute(result);

            }
        }.execute(url);
    }

    public void SetIdAndTitle(final int imageIndex, TextView id1, TextView id2, TextView id3, TextView id4,
                              TextView title1, TextView title2, TextView title3, TextView title4, String id, String title) {

        if (imageIndex == 0) {
            id1.setText(id);
            title1.setText(title);
            title1.setMovementMethod(ScrollingMovementMethod.getInstance());
        } else if (imageIndex == 1) {
            id2.setText(id);
            title2.setText(title);
            title2.setMovementMethod(ScrollingMovementMethod.getInstance());
        } else if (imageIndex == 2) {
            id3.setText(id);
            title3.setText(title);
            title3.setMovementMethod(ScrollingMovementMethod.getInstance());
        } else if (imageIndex == 3) {
            id4.setText(id);
            title4.setText(title);
            title4.setMovementMethod(ScrollingMovementMethod.getInstance());

        }

    }
}
