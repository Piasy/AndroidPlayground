package com.github.piasy.androidplayground.appmarshmallow;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTest();
            }
        });
    }

    private static final int REQUEST_PERMISSION = 1001;

    private void doTest() {
        switch (checkSelfPermission(Manifest.permission.READ_CONTACTS)) {
            case PackageManager.PERMISSION_GRANTED:
                Log.d("RuntimePermissionTest", "status: PERMISSION_GRANTED");
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    Log.d("RuntimePermissionTest",
                            "status: shouldShowRequestPermissionRationale true");
                } else {
                    Log.d("RuntimePermissionTest",
                            "status: shouldShowRequestPermissionRationale false");
                }
                break;
            case PackageManager.PERMISSION_DENIED:
                Log.d("RuntimePermissionTest", "status: PERMISSION_DENIED");
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    Log.d("RuntimePermissionTest",
                            "status: shouldShowRequestPermissionRationale true");
                } else {
                    Log.d("RuntimePermissionTest",
                            "status: shouldShowRequestPermissionRationale false");
                }
                break;
            default:
                Log.d("RuntimePermissionTest", "status: PERMISSION_DENIED");
                break;
        }

        requestPermissions(new String[] { Manifest.permission.READ_CONTACTS },
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.READ_CONTACTS) &&
                    grantResults.length == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("RuntimePermissionTest",
                            "request result: PackageManager.PERMISSION_GRANTED");
                } else {
                    Log.d("RuntimePermissionTest",
                            "request result: PackageManager.PERMISSION_DENIED");
                }
            } else {
                Log.d("RuntimePermissionTest", "request result: bad result");
            }
            logContacts(getSystemContacts());
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private Map<String, String> getSystemContacts() {
        Log.d("RuntimePermissionTest", "getSystemContacts");
        ContentResolver contentResolver = getContentResolver();
        Map<String, String> phones = new HashMap<>();
        Cursor cur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, null);
        if (cur == null || cur.getCount() == 0) {
            if (cur != null) {
                cur.close();
            }
            return phones;
        }
        while (cur.moveToNext()) {
            String name = cur.getString(
                    cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cur.getString(
                    cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (!TextUtils.isEmpty(phone) && !phones.containsKey(phone)) {
                phones.put(phone, name);
            }
        }
        cur.close();
        return phones;
    }

    private void logContacts(Map<String, String> contacts) {
        Log.d("RuntimePermissionTest", "System contacts:");
        for (String phone : contacts.keySet()) {
            Log.d("RuntimePermissionTest", "\t" + phone + " ==> " + contacts.get(phone));
        }
    }
}
