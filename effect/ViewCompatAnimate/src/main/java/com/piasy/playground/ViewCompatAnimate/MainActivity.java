package com.piasy.playground.ViewCompatAnimate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    final View src = findViewById(R.id.src);
    final View dst = findViewById(R.id.dst);

    findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //if (true) {
        //  startActivity(new Intent(MainActivity.this, ExoActivity.class));
        //  return;
        //}

        int[] srcLoc = new int[2];
        src.getLocationOnScreen(srcLoc);
        int[] dstLoc = new int[2];
        dst.getLocationOnScreen(dstLoc);

        ViewCompat.animate(src)
            .withLayer()
            // right
            .translationX((dst.getWidth() / 2 + dstLoc[0]) - (src.getWidth() / 2 + srcLoc[0]))
            .translationY((dst.getHeight() / 2 + dstLoc[1]) - (src.getHeight() / 2 + srcLoc[1]))
            // wrong
            //.translationX((dstLoc[0]) - (srcLoc[0]))
            //.translationY((dstLoc[1]) - (srcLoc[1]))
            .scaleX((float) dst.getWidth() / src.getWidth())
            .scaleY((float) dst.getHeight() / src.getHeight())
            .rotation(0f)
            .setDuration(500);
      }
    });
  }
}
