package br.com.smsforward.services;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import br.com.smsforward.BackgroundServiceCallback;
import br.com.smsforward.Result;
import br.com.smsforward.model.Message;

/**
 * Fields available in the query in content://sms/inbox
 * _id: 1
 * type: 1
 * service_center: null
 * address: 6505551212
 * creator: com.google.android.apps.messaging
 * person: null
 * error_code: 0
 * sub_id: 1
 * read: 1
 * date: 1657167992474
 * body: Android is always a sweet treat!
 * date_sent: 1657167992000
 * address: 6505551212
 * creator: com.google.android.apps.messaging
 * person: null
 * error_code: 0
 * sub_id: 1
 * read: 1
 * date: 1657167992474
 * body: Android is always a sweet treat!
 * date_sent: 1657167992000
 * reply_path_present: 0
 * protocol: 0
 * seen: 1
 * thread_id: 2
 * status: -1
 * locked: 0
 * subject: null
 */
public class QueryMessagesService {
    private final ContentResolver contentResolver;

    public QueryMessagesService(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void findAll(BackgroundServiceCallback<List<Message>> callback, Executor executor) {
        executor.execute(() -> {
            try {
                List<Message> messages = findAllSync();
                Result<List<Message>> result = new Result<>(messages);
                callback.onComplete(result);
            } catch (Exception e) {
                Result result = new Result(e);
                callback.onComplete(result);
            }
        });
    }

    public List<Message> findAllSync() {
        Cursor cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        List<Message> messages = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                Map<String, String> msgData = new HashMap<>();
                for(int i = 0; i < cursor.getColumnCount(); i++) {
                    msgData.put(cursor.getColumnName(i), cursor.getString(i));
                }

                messages.add(new Message(
                        Long.parseLong(msgData.get("_id")),
                        msgData.get("address"),
                        msgData.get("creator"),
                        msgData.get("person"),
                        Long.parseLong(msgData.get("date")),
                        Long.parseLong(msgData.get("date_sent")),
                        msgData.get("body")
                ));
            } while(cursor.moveToNext());
        }

        return messages;
    }
}
