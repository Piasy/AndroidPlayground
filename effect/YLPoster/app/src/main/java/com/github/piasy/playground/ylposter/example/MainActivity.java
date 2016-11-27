package com.github.piasy.playground.ylposter.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.piasy.playground.ylposter.PosterState;

public class MainActivity extends AppCompatActivity {

    static final Uri URI = Uri.parse("http://192.168.25.62:8000/WechatIMG18.jpg");
    static final Uri POSTER_URI = Uri.parse("http://192.168.1.113:8000/5.jpg");
    static final Uri POSTER_URI_ADHERE = Uri.parse("http://192.168.1.113:8000/6.jpg");
    static final PosterState DEFAULT_STATE = PosterState.builder()
            .title("Piasy")
            .desc("")
            .slogan("优秀是一种习惯")
            .bg(URI.toString())
            .build();

    static boolean sIsSnow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.mPreview)
    public void snowPreview() {
        sIsSnow = true;
        startActivity(new Intent(this, PreviewActivity.class));
    }

    @OnClick(R2.id.mSnowEdit)
    public void snowEdit() {
        sIsSnow = true;
        startActivity(new Intent(this, EditActivity.class));
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

    @OnClick(R2.id.mAdhereDisplay)
    public void adhereDisplay() {
        sIsSnow = false;
        startActivity(new Intent(this, DisplayActivity.class));
    }
}
