package br.com.smsforward.workers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import br.com.smsforward.data.Database;
import br.com.smsforward.services.MessageService;

public class SmsObserverWorker extends Worker {
    public static final String WORKER_NAME = "SmsObserver";
    private final MessageService messageService;

    public SmsObserverWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        Database.initDatabase(context);
        messageService = new MessageService();
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
//            Cursor cursor = getApplicationContext().getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
//            if(cursor.moveToFirst()) {
//                do {
//                    Map<String, String> msgData = new HashMap<>();
//                    for(int i = 0; i < cursor.getColumnCount(); i++) {
//                        msgData.put(cursor.getColumnName(i), cursor.getString(i));
//                    }
//
//                    msgData.keySet().forEach(key -> {
//                        Log.i(getClass().getCanonicalName(), key + ": " + msgData.get(key));
//                    });
//                } while(cursor.moveToNext());
//            }
            // Per minute
            for(int minutes = 0; minutes < 15; minutes++) {
                for(int tenSecondsInterval = 0; tenSecondsInterval < 6; tenSecondsInterval++) {
                    messageService.integrateMessages(getApplicationContext().getContentResolver());
                    Thread.sleep(10000);
                }
            }
            return Result.success();
        } catch (Exception e) {
            return Result.failure();
        }
    }
}
