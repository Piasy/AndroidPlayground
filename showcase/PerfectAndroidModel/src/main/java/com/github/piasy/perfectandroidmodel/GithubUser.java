package com.github.piasy.perfectandroidmodel;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;

/**
 * Created by Piasy{github.com/Piasy} on 7/30/16.
 */
@AutoValue
public abstract class GithubUser implements GithubUserModel, Parcelable {

    static final Factory<GithubUser> FACTORY = new Factory<>(AutoValue_GithubUser::new,
            new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()));

    static final RowMapper<GithubUser> MAPPER = FACTORY.get_allMapper();

    public static TypeAdapter<GithubUser> typeAdapter(final Gson gson) {
        return new AutoValue_GithubUser.GsonTypeAdapter(gson);
    }

}
