package com.github.piasy.taskdemo.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.github.piasy.taskdemo.R;

public class SingleTop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskDemo", "SingleTop onCreate");

        setContentView(R.layout.activity_single_top);

        findViewById(R.id.mSingleTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleTop.this, SingleTop.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TaskDemo", "SingleTop onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TaskDemo", "SingleTop onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TaskDemo", "SingleTop onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TaskDemo", "SingleTop onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TaskDemo", "SingleTop onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d("TaskDemo", "SingleTop onNewIntent");
    }
}
