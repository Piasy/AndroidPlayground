package com.github.piasy.taskdemo.launch_mode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.piasy.taskdemo.R;

public class SingleTaskWithoutTaskAffinity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task_without_task_affinity);
    }
}
