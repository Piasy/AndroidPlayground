package com.github.piasy.playground.reproduce.mockwechat;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MockWeChatActivity extends AppCompatActivity {

    private final String mPkg = "com.github.piasy.playground.reproduce.taskswitchbug";
    private final String mFullName
            = "com.github.piasy.playground.reproduce.taskswitchbug.MockPaymentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_we_chat);

        findViewById(R.id.mBtnPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(mPkg, mFullName));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("MockWeChatActivity", "success");
                startActivity(intent);
                finish();
            }
        });
    }
}
