package com.github.piasy.playground.reproduce.taskswitchbug;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent == null) {
            gotoHome();
            return;
        }
        String action = intent.getAction();
        if (!action.equals("android.intent.action.VIEW")) {
            gotoHome();
            return;
        }
        if (intent.getData() == null || TextUtils.isEmpty(intent.getData().toString())) {
            gotoHome();
            return;
        }
        gotoInvite();
    }

    private void gotoHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void gotoInvite() {
        startActivity(new Intent(this, InviteActivity.class));
        finish();
    }
}
