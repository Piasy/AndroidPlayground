package com.github.piasy.perfectandroidmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.threeten.bp.Duration;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 7/30/16.
 */

public class Showcase {
    void retrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubApi api = retrofit.create(GithubApi.class);
        Observable<List<GithubUser>> following = api.following("Piasy");

        following.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followings -> {
                    // success
                }, err -> {
                    // fail
                });
    }

    void handWriteOkHttp() {
        final Gson gson = new GsonBuilder().create();
        final OkHttpClient client = new OkHttpClient();

        Observable<List<GithubUser>> following =
                Observable.create(subscriber -> {
                    Request request = new Request.Builder()
                            .url("https://api.github.com/Piasy/following")
                            .get()
                            .build();
                    try {
                        String body = client.newCall(request)
                                .execute().body().string();
                        List<GithubUser> users = gson.fromJson(body,
                                new TypeToken<List<GithubUser>>() { }
                                        .getType());
                        subscriber.onNext(users);
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                });

        following.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followings -> {
                    // success
                }, err -> {
                    // fail
                });
    }

    void sqlBrite(List<GithubUser> users, Context context) {
        BriteDatabase briteDb = SqlBrite.create().wrapDatabaseHelper(
                new DbOpenHelper(context), Schedulers.io());

        BriteDatabase.Transaction transaction = briteDb.newTransaction();
        try {
            for (int i = 0, size = users.size(); i < size; i++) {
                briteDb.insert(GithubUser.TABLE_NAME,
                        GithubUser.FACTORY.marshal(users.get(i))
                                .asContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    void SQLiteOpenHelper(List<GithubUser> users, Context context, DateTimeFormatter formatter) {
        DbOpenHelper helper = new DbOpenHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.beginTransaction();
        try {
            for (int i = 0, size = users.size(); i < size; i++) {
                GithubUser githubUser = users.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(GithubUser.ID, githubUser.id());
                contentValues.put(GithubUser.LOGIN, githubUser.login());
                if (githubUser.created_at() != null) {
                    contentValues.put(GithubUser.CREATED_AT,
                            formatter.format(githubUser.created_at()));
                }
                database.insert(GithubUser.TABLE_NAME, null, contentValues);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    void sqlBrite2(GithubUser githubUser, BriteDatabase briteDb) {
        briteDb.insert(GithubUser.TABLE_NAME,
                GithubUser.FACTORY.marshal(githubUser).asContentValues(),
                SQLiteDatabase.CONFLICT_REPLACE);

        Cursor cursor = briteDb.query(GithubUser.TABLE_NAME, GithubUser.GET_BY_ID, "1");
        GithubUser read = GithubUser.MAPPER.map(cursor);
        cursor.close();
    }

    void handWriteDB(GithubUser githubUser, SQLiteDatabase database, DateTimeFormatter formatter) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GithubUser.ID, githubUser.id());
        contentValues.put(GithubUser.LOGIN, githubUser.login());
        if (githubUser.created_at() != null) {
            contentValues.put(GithubUser.CREATED_AT, formatter.format(githubUser.created_at()));
        }
        database.insert(GithubUser.TABLE_NAME, null, contentValues);

        Cursor cursor = database.query(GithubUser.TABLE_NAME,
                new String[] { GithubUser.ID, GithubUser.LOGIN, GithubUser.CREATED_AT }, "id = ?",
                new String[] { "1" }, null, null, null);
        GithubUser read =
                new AutoValue_GithubUser(cursor.getLong(cursor.getColumnIndex(GithubUser.ID)),
                        cursor.getString(cursor.getColumnIndex(GithubUser.LOGIN)), formatter.parse(
                        cursor.getString(cursor.getColumnIndex(GithubUser.CREATED_AT)),
                        ZonedDateTime.FROM));
        cursor.close();
    }

    void merge(Observable<List<GithubUser>> local, Observable<List<GithubUser>> network) {
        Observable.concat(local, network)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followings -> {
                    //
                }, err -> {
                    // 集中错误处理
                });
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = null;

        ZonedDateTime time = ZonedDateTime.of(2016, 6, 10, 18, 29, 0, 0, ZoneId.systemDefault());
        ZonedDateTime after = time.plusDays(2).plusHours(3);
        Duration duration = Duration.between(time, after);
        System.out.println(time.format(formatter)
                + ", "
                + after.format(formatter)
                + ", "
                + duration.toHours());
        // 2016/06/10 18:29:00, 2016/06/12 21:29:00, 51
    }
}
