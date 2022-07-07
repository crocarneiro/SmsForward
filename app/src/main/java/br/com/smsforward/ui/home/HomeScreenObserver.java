package br.com.smsforward.ui.home;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import br.com.smsforward.R;
import br.com.smsforward.workers.SmsObserverWorker;

public class HomeScreenObserver implements LifecycleEventObserver {
    private final AppCompatActivity activity;
    private WorkManager workManager;
    private Button button;

    public HomeScreenObserver(Fragment fragment) {
        this.activity = (AppCompatActivity) fragment.getActivity();
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if(event.getTargetState() == Lifecycle.State.STARTED || event.getTargetState() == Lifecycle.State.RESUMED) {
            workManager = WorkManager.getInstance(activity);
            button = activity.findViewById(R.id.home_screen_start_btn);

            setupScreen();

            button.setOnClickListener(v -> {
                if(isServiceRunning()) {
                    workManager.cancelUniqueWork(SmsObserverWorker.WORKER_NAME);
                } else {
                    PeriodicWorkRequest watchSmsWorkRequest = new PeriodicWorkRequest.Builder(SmsObserverWorker.class, 15, TimeUnit.MINUTES)
                            .build();

                    workManager.enqueueUniquePeriodicWork(SmsObserverWorker.WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, watchSmsWorkRequest);
                }

                setupScreen();
            });
        }
    }

    private void setupScreen() {
        ConstraintLayout container = activity.findViewById(R.id.home_screen_service_running_container);
        if(isServiceRunning()) {
            container.setVisibility(View.INVISIBLE);
            button.setText(R.string.start_button);
            button.setBackgroundColor(activity.getResources().getColor(R.color.green));
        } else {
            container.setVisibility(View.VISIBLE);
            button.setText(R.string.stop_button);
            button.setBackgroundColor(activity.getResources().getColor(R.color.red));
        }
    }

    private boolean isServiceRunning() {
        ListenableFuture<List<WorkInfo>> listListenableFuture = workManager.getWorkInfosForUniqueWork(SmsObserverWorker.WORKER_NAME);
        try {
            List<WorkInfo> infoList = listListenableFuture.get();

            infoList.forEach(workInfo -> {
                Log.i(getClass().getCanonicalName(), "Id = " + workInfo.getId());
                Log.i(getClass().getCanonicalName(), "State = " + workInfo.getState());
            });

            return infoList.stream().filter(e -> e.getState().isFinished()).count() == 0;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }
}