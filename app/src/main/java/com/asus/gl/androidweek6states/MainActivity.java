package com.asus.gl.androidweek6states;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainAct";

    private int clicks;
    private String content= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clicks=0;

        SharedPreferences pref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        clicks= pref.getInt("clicks",0);
        Log.i(TAG, "onCreate was called");


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("http://ybu.edu.tr/muhendislik/bilgisayar/").get();

                    Element element = doc.select("div.contentAnnouncements").first();

                    content = element.text();
                    Log.i(TAG,content);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "JSoup error", e);
                }

            }
        });

        t.start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG,"onResume was called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart was called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart was called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause was called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop was called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy was called");

        SharedPreferences pref = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("clicks", clicks);
        edit.commit();
    }

    public void changeActivity(View view) {

        //Intent intent = new Intent(this,SecondActivity.class);
        //startActivity(intent);
        clicks++;
        Toast.makeText(this, "Current click :" + clicks, Toast.LENGTH_SHORT).show();


        TextView t = findViewById(R.id.txtRes);
        t.setText(content);

    }


}
