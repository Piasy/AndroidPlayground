package com.github.piasy.playground.reproduce.taskswitchbug;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class InviteActivity extends AppCompatActivity {

    private String mOrder = "{\"amount\":1,\"amount_refunded\":0,\"amount_settle\":1,"
                            + "\"app\":\"invalid test data\",\"body\":\"invalid test data\","
                            + "\"channel\":\"wx\",\"client_ip\":\"58.132.177.2\","
                            + "\"created\":1480576812,\"credential\":{\"object\":\"credential\","
                            + "\"wx\":{\"appId\":\"invalid test data\","
                            + "\"nonceStr\":\"invalid test data\","
                            + "\"packageValue\":\"Sign=WXPay\",\"partnerId\":\"1254738801\","
                            + "\"prepayId\":\"invalid test data\","
                            + "\"sign\":\"invalid test data\","
                            + "\"timeStamp\":1480576812}},\"currency\":\"cny\",\"extra\":[],"
                            + "\"id\":\"invalid test data\",\"livemode\":true,"
                            + "\"metadata\":[],\"object\":\"charge\","
                            + "\"order_no\":\"invalid test data\",\"paid\":false,"
                            + "\"refunded\":false,\"refunds\":{\"data\":[],\"has_more\":false,"
                            + "\"object\":\"list\","
                            + "\"url\":\"/v1/charges/invalid test data/refunds\"},"
                            + "\"subject\":\"invalid test data\",\"time_expire\":1480584012}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        findViewById(R.id.mBtnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MockPaymentActivity.mockPay(InviteActivity.this);

                // 启动微信支付，有问题
                //Pingpp.createPayment(InviteActivity.this, mOrder);
                //startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1011);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(HomeActivity.TAG,
                "InviteActivity onActivityResult " + requestCode + ", " + resultCode + ", " + data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
