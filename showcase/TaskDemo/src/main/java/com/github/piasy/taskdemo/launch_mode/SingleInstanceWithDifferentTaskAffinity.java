package com.github.piasy.taskdemo.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.github.piasy.taskdemo.R;

public class SingleInstanceWithDifferentTaskAffinity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onCreate");

        setContentView(R.layout.activity_single_instance_with_different_task_affinity);

        findViewById(R.id.mSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleInstanceWithDifferentTaskAffinity.this,
                        SimpleActivity.class));
            }
        });

        findViewById(R.id.mSimpleWithDiffTaskAff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleInstanceWithDifferentTaskAffinity.this,
                        SimpleWithDifferentTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleTaskWithoutTaskAff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingleInstanceWithDifferentTaskAffinity.this,
                        SingleTaskWithoutTaskAffinity.class));
            }
        });

        findViewById(R.id.mSingleTaskWithDiffTaskAff).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SingleInstanceWithDifferentTaskAffinity.this,
                                SingleTaskWithDifferentTaskAffinity.class));
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onPause");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onNewIntent");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("TaskDemo", "SingleInstanceWithDifferentTaskAffinity onResume");
    }
}
