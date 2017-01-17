package com.github.piasy.taskdemo.launch_mode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.piasy.taskdemo.R;

public class SingleTaskWithDifferentTaskAffinity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task_with_different_task_affinity);
    }
}
