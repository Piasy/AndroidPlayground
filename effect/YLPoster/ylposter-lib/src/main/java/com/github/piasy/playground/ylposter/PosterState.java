package com.github.piasy.playground.ylposter;

import android.content.SharedPreferences;
import com.google.auto.value.AutoValue;

/**
 * Created by Piasy{github.com/Piasy} on 26/11/2016.
 */

@AutoValue
public abstract class PosterState {
    private static final String SP_KEY_TITLE = "PosterState_SP_KEY_TITLE";
    private static final String SP_KEY_DESC = "PosterState_SP_KEY_DESC";
    private static final String SP_KEY_SLOGAN = "PosterState_SP_KEY_SLOGAN";
    private static final String SP_KEY_BG = "PosterState_SP_KEY_BG";

    public static Builder builder() {
        return new AutoValue_PosterState.Builder();
    }

    public static PosterState read(SharedPreferences preferences, PosterState defaultState) {
        return builder()
                .title(preferences.getString(SP_KEY_TITLE, defaultState.title()))
                .desc(preferences.getString(SP_KEY_DESC, defaultState.desc()))
                .slogan(preferences.getString(SP_KEY_SLOGAN, defaultState.slogan()))
                .bg(preferences.getString(SP_KEY_BG, defaultState.bg()))
                .build();
    }

    public abstract String title();

    public abstract String desc();

    public abstract String slogan();

    public abstract String bg();

    public void save(SharedPreferences preferences) {
        preferences.edit()
                .putString(SP_KEY_TITLE, title())
                .putString(SP_KEY_DESC, desc())
                .putString(SP_KEY_SLOGAN, slogan())
                .putString(SP_KEY_BG, bg())
                .commit();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder desc(String desc);

        public abstract Builder slogan(String slogan);

        public abstract Builder bg(String bg);

        public abstract PosterState build();
    }
}
