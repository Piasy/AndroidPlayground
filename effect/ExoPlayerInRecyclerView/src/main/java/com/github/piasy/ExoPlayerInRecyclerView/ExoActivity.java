package com.github.piasy.ExoPlayerInRecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.source.MediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheEvictor;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.EventLogger;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExoActivity extends AppCompatActivity {

  private String url =
      "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_exo);

    File cacheFolder = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
    CacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024);
    Cache cache = new SimpleCache(cacheFolder, evictor, new ExoDatabaseProvider(this));
    MediaSourceFactory mediaSourceFactory = new ProgressiveMediaSource.Factory(
        new CacheDataSourceFactory(cache, new DefaultDataSourceFactory(this, "Test")));

    //test(mediaSourceFactory);
    testRv(mediaSourceFactory);
  }

  private void testRv(MediaSourceFactory mediaSourceFactory) {
    RecyclerView rv = findViewById(R.id.rv);
    rv.setVisibility(View.VISIBLE);
    Adapter adapter = new Adapter(mediaSourceFactory);
    rv.setLayoutManager(new GridLayoutManager(this, 1));
    rv.setAdapter(adapter);
  }

  private void test(MediaSourceFactory mediaSourceFactory) {
    MappingTrackSelector trackSelector = new DefaultTrackSelector(this);
    SimpleExoPlayer player = new SimpleExoPlayer.Builder(this,
        new DefaultRenderersFactory(this))
        .setTrackSelector(trackSelector)
        .build();
    player.setPlayWhenReady(true);
    player.setRepeatMode(Player.REPEAT_MODE_ONE);
    player.prepare(
        mediaSourceFactory.createMediaSource(
            Uri.fromFile(new File(getExternalFilesDir(null), "BM.mkv"))));
    PlayerView playerView = findViewById(R.id.player_view);
    playerView.setVisibility(View.VISIBLE);
    playerView.setPlayer(player);

    TextView debugView = new TextView(this);
    playerView.getOverlayFrameLayout().addView(debugView);
    DebugTextViewHelper helper = new DebugTextViewHelper(player, debugView);
    helper.start();

    player.addAnalyticsListener(new EventLogger(trackSelector));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    PlayerView playerView;
    SimpleExoPlayer player;

    public ViewHolder(@NonNull final View itemView) {
      super(itemView);
      playerView = itemView.findViewById(R.id.player_view);
      playerView.setUseController(false);
    }
  }

  static class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MediaSourceFactory mediaSourceFactory;
    private final List<String> urls = Arrays.asList(
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4",
        "https://media1.giphy.com/media/S79C8eowG7PV75a7Fm/giphy.mp4?cid=3a12e166b592a9599e815d25045e7e261c9e671d0102a83c&rid=giphy.mp4",
        "https://media2.giphy.com/media/SqktZEmYhHicMx8lHT/giphy.mp4?cid=3a12e166d4e5811b40bd254f8a3b0d2b2e4d0a66af8392b3&rid=giphy.mp4"
    );

    Adapter(MediaSourceFactory mediaSourceFactory) {
      this.mediaSourceFactory = mediaSourceFactory;
    }

    @Override public void onViewRecycled(@NonNull ViewHolder holder) {
      super.onViewRecycled(holder);
      SimpleExoPlayer player = holder.player;
      holder.player = null;
      if (player != null) {
        executorService.execute(player::release);
      }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
      return new ViewHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
      Context context = holder.itemView.getContext();
      holder.player = new SimpleExoPlayer.Builder(context,
          new DefaultRenderersFactory(context))
          .setTrackSelector(new DefaultTrackSelector(context))
          .build();
      holder.player.setPlayWhenReady(true);
      holder.player.setRepeatMode(Player.REPEAT_MODE_ONE);
      holder.player.prepare(
          mediaSourceFactory.createMediaSource(Uri.parse(urls.get(position))));
      holder.playerView.setPlayer(holder.player);
    }

    @Override
    public int getItemCount() {
      return urls.size();
    }
  }
}