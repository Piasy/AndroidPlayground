package com.piasy.playground.InsertMediaStore;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

  private static final String VOLUME_EXTERNAL = "external";
  private static final String VOLUME_INTERNAL = "internal";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    findViewById(R.id.external_public).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        // TODO: not possible without preserveLegacyExternalStorage?
      }
    });
    findViewById(R.id.external_private).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        test("external_private.jpg", new File(getExternalFilesDir(null), "external_private.jpg"));
      }
    });
    findViewById(R.id.internal_private).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        test("internal_private.jpg", new File(getFilesDir(), "internal_private.jpg"));
      }
    });
    findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String external = search("external_private.jpg");
        String internal = search("internal_private.jpg");
        Log.e("MainActivity", "search result: external " + external + ", internal " + internal);
      }
    });

    MainActivityPermissionsDispatcher.checkPermissionWithPermissionCheck(this);
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
  }

  @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
  void checkPermission() {

  }

  private void test(final String input, final File output) {
    new Thread(new Runnable() {
      @Override public void run() {
        try {
          InputStream inputStream = getAssets().open(input);
          FileOutputStream outputStream = new FileOutputStream(output);
          byte[] buffer = new byte[100 * 1024];
          int read;
          while ((read = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, read);
          }
          inputStream.close();
          outputStream.close();

          Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
          Uri finishedContentUri;
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            finishedContentUri = FileProvider.getUriForFile(MainActivity.this,
                "com.piasy.playground.InsertMediaStore.providers.fileprovider", output);
          } else {
            finishedContentUri = Uri.fromFile(output);
          }
          mediaScanIntent.setData(finishedContentUri);
          mediaScanIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mediaScanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          }
          sendBroadcast(mediaScanIntent);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private String search(String fileName) {
    String data = MediaStore.Files.FileColumns.DATA;
    final String[] projection = { data };
    final String selection = MediaStore.Files.FileColumns.DISPLAY_NAME + " = ?";
    final String[] selectionArgs = { fileName };
    Cursor cursor = getContentResolver().query(
        MediaStore.Files.getContentUri(VOLUME_EXTERNAL), projection, selection,
        selectionArgs, null);

    String path = checkFileInStorage(cursor, data);
    if (path == null) {
      cursor = getContentResolver().query(
          MediaStore.Files.getContentUri(VOLUME_INTERNAL), projection, selection,
          selectionArgs, null);
      path = checkFileInStorage(cursor, data);
    }
    return path;
  }

  private String checkFileInStorage(Cursor cursor, String data) {
    if (cursor != null && cursor.moveToFirst()) {
      int dataColumn = cursor.getColumnIndexOrThrow(data);
      String path = cursor.getString(dataColumn);
      cursor.close();
      cursor = null;
      if (new File(path).exists()) {
        return path;
      }
    }

    if (cursor != null) {
      cursor.close();
    }

    return null;
  }
}
