package com.github.piasy.playground.ylposter.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    static boolean sIsSnow = false;
    static final Uri URI = Uri.parse("http://192.168.1.113:8000/WechatIMG18.jpg");
    static final Uri POSTER_URI = Uri.parse("http://192.168.1.113:8000/5.jpg");
    static final Uri POSTER_URI_ADHERE = Uri.parse("http://192.168.1.113:8000/6.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.mSnowEdit)
    public void snowEdit() {
        sIsSnow = true;
        startActivity(new Intent(this, EditActivity.class));
    }

    @OnClick(R2.id.mSnowPreview)
    public void snowPreview() {
        sIsSnow = true;
        startActivity(new Intent(this, PreviewActivity.class));
    }

    @OnClick(R2.id.mSnowDisplay)
    public void snowDisplay() {
        sIsSnow = true;
        startActivity(new Intent(this, DisplayActivity.class));
    }

    @OnClick(R2.id.mAdhereEdit)
    public void adhereEdit() {
        sIsSnow = false;
        startActivity(new Intent(this, EditActivity.class));
    }

    @OnClick(R2.id.mAdherePreview)
    public void adherePreview() {
        sIsSnow = false;
        startActivity(new Intent(this, PreviewActivity.class));
    }

    @OnClick(R2.id.mAdhereDisplay)
    public void adhereDisplay() {
        sIsSnow = false;
        startActivity(new Intent(this, DisplayActivity.class));
    }
}
