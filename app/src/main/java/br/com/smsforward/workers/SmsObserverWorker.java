package br.com.smsforward.workers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SmsObserverWorker extends Worker {
    public static final String WORKER_NAME = "SmsObserver";

    public SmsObserverWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }

    }
}
