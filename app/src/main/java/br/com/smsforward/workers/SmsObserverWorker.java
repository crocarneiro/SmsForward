package br.com.smsforward.workers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.HashMap;
import java.util.Map;

public class SmsObserverWorker extends Worker {
    public static final String WORKER_NAME = "SmsObserver";

    public SmsObserverWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Cursor cursor = getApplicationContext().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            if(cursor.moveToFirst()) {
                do {
                    Map<String, String> msgData = new HashMap<>();
                    for(int i = 0; i < cursor.getColumnCount(); i++) {
                        msgData.put(cursor.getColumnName(i), cursor.getString(i));
                    }

                    msgData.keySet().forEach(key -> {
                        Log.i(getClass().getCanonicalName(), key + ": " + msgData.get(key));
                    });
                } while(cursor.moveToNext());
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }

    }
}
