package com.github.piasy.taskdemo.launch_mode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.piasy.taskdemo.R;

public class SimpleWithDifferentTaskAffinity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_with_different_task_affinity);
    }
}
