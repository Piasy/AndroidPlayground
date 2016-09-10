package com.github.piasy.instanceofperf;

import android.app.Application;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.github.piasy.aopdemolib.Trace;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testInstanceOf();
        testNameEquals();
    }

    @Trace
    private void testInstanceOf() {
        Application app = getApplication();
        for (int i = 0; i < 1_000_000; i++) {
            if (app instanceof DialogInterface.OnClickListener) {
                Log.d("MainActivity", "can't be there");
            }
        }
    }

    @Trace
    private void testNameEquals() {
        Application app = getApplication();
        String name = app.getClass().getName();
        for (int i = 0; i < 1_000_000; i++) {
            if (name.equals("android.content.DialogInterface$OnClickListener")) {
                Log.d("MainActivity", "can't be there");
            }
        }
    }
}
