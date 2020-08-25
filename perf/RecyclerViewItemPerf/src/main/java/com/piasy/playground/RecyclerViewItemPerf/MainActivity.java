package com.piasy.playground.RecyclerViewItemPerf;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.piasy.playground.RecyclerViewItemPerf.databinding.ItemConstraintLayoutBinding;
import com.piasy.playground.RecyclerViewItemPerf.databinding.ItemNestedLayoutBinding;

public class MainActivity extends AppCompatActivity {

  private HandlerThread metricsThread;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    RecyclerView rv = findViewById(R.id.rv);
    rv.setVisibility(View.VISIBLE);
    //Adapter adapter = new Adapter(R.layout.item_constraint_layout);
    Adapter adapter = new Adapter(R.layout.item_nested_layout);
    rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    rv.setAdapter(adapter);

    metricsThread = new HandlerThread("metrics");
    metricsThread.start();
    getWindow().addOnFrameMetricsAvailableListener(new Window.OnFrameMetricsAvailableListener() {
      @Override public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics,
          int dropCountSinceLastInvocation) {
        FrameMetrics copy = new FrameMetrics(frameMetrics);
        Log.e("MainActivity", "LAYOUT_MEASURE_DURATION " + copy.getMetric(
            FrameMetrics.LAYOUT_MEASURE_DURATION));
      }
    }, new Handler(metricsThread.getLooper()));
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    metricsThread.quit();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull final View itemView) {
      super(itemView);
    }
  }

  static class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private final int layout;

    Adapter(int layout) {
      this.layout = layout;
    }

    @Override public void onViewRecycled(@NonNull ViewHolder holder) {
      super.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      if (layout == R.layout.item_constraint_layout) {
        return new ViewHolder(
            ItemConstraintLayoutBinding.inflate(inflater, parent, false).getRoot());
      } else {
        return new ViewHolder(ItemNestedLayoutBinding.inflate(inflater, parent, false).getRoot());
      }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
      return 1000;
    }
  }
}
