package com.ciTest.com.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ciTest.com.Model.ImageViewFeature;
import com.ciTest.com.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShowPhotoActivity extends AppCompatActivity {


    private LinearLayout show_photo_view_linear;

    private static DisplayMetrics displayMetrics;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView id1;
    private TextView title1;
    private TextView id2;
    private TextView title2;
    private TextView id3;
    private TextView title3;
    private TextView id4;
    private TextView title4;

    private TextView loading;
    private ScrollView s01;

    private String jsonText;

    private int start = 0;
    private int end = 100;

    private int screenY;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_photo);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenY = displayMetrics.heightPixels;

        AllFindView();

        GetPostValue();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        SetViewVisble(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            s01.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    Log.i("bbbb", String.valueOf(scrollY));

                    if (scrollY > screenY) {
                        start = end;
                        end = end + 40;


                        if (end > jsonArray.length()) {
                            end = jsonArray.length();
                        }

                        JsonAnalysis(start, end);
                        screenY = scrollY + displayMetrics.heightPixels;

                    }
                }
            });
        }
    }

    private void AllFindView() {
        show_photo_view_linear = findViewById(R.id.show_photo_view_linear);
        loading = findViewById(R.id.loading);
        s01 = findViewById(R.id.s01);
    }

    private void SetViewVisble(boolean isLoading) {
        if (isLoading) {
            loading.setVisibility(View.VISIBLE);
            s01.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.GONE);
            s01.setVisibility(View.VISIBLE);
        }
    }

    private void GetPostValue() {

        String postUrl = "https://jsonplaceholder.typicode.com/photos";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest getRequest = new StringRequest(postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        jsonText = response;
                        JsonAnalysis(start, end);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        requestQueue.add(getRequest);
    }


    @SuppressLint("StaticFieldLeak")
    private void JsonAnalysis(int start, int end) {
        try {

            ImageViewFeature imageViewFeature = new ImageViewFeature();
            jsonArray = new JSONArray(jsonText);


            for (int i = start; i < end; i++) {


                String id = jsonArray.getJSONObject(i).getString("id");
                String title = jsonArray.getJSONObject(i).getString("title");
                String thumbnailUrl = jsonArray.getJSONObject(i).getString("thumbnailUrl");

                int imageIndex = (i % 4);


                if (i % 4 == 0) {
                    ImageCustomViewAllFindView();
                }


                if (imageIndex == 0) {
                    Glide.with(this).load(thumbnailUrl).into(image1);
                } else if (imageIndex == 1) {
                    Glide.with(this).load(thumbnailUrl).into(image2);
                } else if (imageIndex == 2) {
                    Glide.with(this).load(thumbnailUrl).into(image3);
                } else if (imageIndex == 3) {
                    Glide.with(this).load(thumbnailUrl).into(image4);
                }

                imageViewFeature.SetIdAndTitle(imageIndex, id1, id2, id3, id4, title1, title2, title3, title4, id, title);

                SetViewVisble(false);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ImageCustomViewAllFindView() {
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.image_custom_view, null);

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(displayMetrics.widthPixels / 4, displayMetrics.widthPixels / 4);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(displayMetrics.widthPixels / 4, displayMetrics.widthPixels / 4, 1);

        image1 = v.findViewById(R.id.image1);
        image1.setLayoutParams(relativeLayoutParams);
        image2 = v.findViewById(R.id.image2);
        image2.setLayoutParams(relativeLayoutParams);
        image3 = v.findViewById(R.id.image3);
        image3.setLayoutParams(relativeLayoutParams);
        image4 = v.findViewById(R.id.image4);
        image4.setLayoutParams(relativeLayoutParams);

        RelativeLayout r01 = v.findViewById(R.id.r01);
        r01.setLayoutParams(linearLayoutParams);
        RelativeLayout r02 = v.findViewById(R.id.r02);
        r02.setLayoutParams(linearLayoutParams);
        RelativeLayout r03 = v.findViewById(R.id.r03);
        r03.setLayoutParams(linearLayoutParams);
        RelativeLayout r04 = v.findViewById(R.id.r04);
        r04.setLayoutParams(linearLayoutParams);

        id1 = v.findViewById(R.id.id1);
        title1 = v.findViewById(R.id.title1);

        id2 = v.findViewById(R.id.id2);
        title2 = v.findViewById(R.id.title2);

        id3 = v.findViewById(R.id.id3);
        title3 = v.findViewById(R.id.title3);

        id4 = v.findViewById(R.id.id4);
        title4 = v.findViewById(R.id.title4);

        show_photo_view_linear.addView(v);
    }
}
