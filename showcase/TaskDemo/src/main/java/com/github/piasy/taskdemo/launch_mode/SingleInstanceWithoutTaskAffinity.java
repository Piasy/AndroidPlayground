package com.github.piasy.taskdemo.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.github.piasy.taskdemo.R;

public class SingleInstanceWithoutTaskAffinity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance_without_task_affinity);

        findViewById(R.id.mSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(SingleInstanceWithoutTaskAffinity.this, SimpleActivity.class));
            }
        });
    }
}
