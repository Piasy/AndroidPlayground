package com.github.piasy.playground.reproduce.taskswitchbug;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MockPaymentActivity extends Activity {

    public static final int REQ_CODE = 1010;

    private static final String FLAG = "MockPaymentActivity.PAY_REQUEST";

    private final String mPkg = "com.github.piasy.playground.reproduce.mockwechat";
    private final String mFullName
            = "com.github.piasy.playground.reproduce.mockwechat.MockWeChatActivity";

    public static void mockPay(Activity activity) {
        Intent intent = new Intent(activity, MockPaymentActivity.class);
        intent.putExtra(FLAG, "req");
        activity.startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(HomeActivity.TAG,
                "MockPaymentActivity onCreate " + getIntent() + " " + savedInstanceState);
        String flag = getIntent().getStringExtra(FLAG);
        if (flag != null) {
            // pay request
            Log.d(HomeActivity.TAG, "MockPaymentActivity onCreate pay request");
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mPkg, mFullName));
            startActivity(intent);
        } else {
            // WeChat result
            Log.d(HomeActivity.TAG, "MockPaymentActivity onCreate WeChat result");
            setResult(Activity.RESULT_OK, getIntent());
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(HomeActivity.TAG, "MockPaymentActivity onNewIntent " + intent);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
