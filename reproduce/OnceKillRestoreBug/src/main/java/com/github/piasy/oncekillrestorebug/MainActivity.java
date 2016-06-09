package com.github.piasy.oncekillrestorebug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import jonathanfinerty.once.Once;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "OnceKillRestoreBug";
    private static final String KEY2 = "OnceKillRestoreBug2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Once.initialise(getApplicationContext());

        if (!Once.beenDone(KEY)) {
            Once.toDo(KEY);
        }
        if (!Once.beenDone(KEY2)) {
            Once.toDo(KEY2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Once.needToDo(KEY)) {
            Log.d(KEY, "needToDo");
            Once.markDone(KEY);
        }
        if (Once.needToDo(KEY2)) {
            Log.d(KEY, "needToDo2");
            Once.markDone(KEY2);
        }
    }
}
