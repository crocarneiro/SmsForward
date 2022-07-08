package br.com.smsforward.services;

import android.content.ContentResolver;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;
import br.com.smsforward.data.Database;
import br.com.smsforward.model.Message;
import br.com.smsforward.repositories.MessageRepository;

/**
 * This class provides methods to synchronize the device SMSs with this app database.
 */
public class SyncMessagesService {
    private final MessageRepository messageRepository;

    public SyncMessagesService() {
        this.messageRepository = Database.getDatabase().messageRepository();
    }

    public void syncAll(ContentResolver contentResolver, Executor executor) {
        QueryMessagesService queryMessagesService = new QueryMessagesService(contentResolver);
        queryMessagesService.findAll(result -> {
            if(result.success()) {
                result.getData().forEach(this::syncMessage);
            } else {
                result.getError().printStackTrace();
                Log.e(getClass().getCanonicalName(), "Error synchronizing SMSs: " + result.getError().getMessage());
            }

        }, executor);
    }

    public void syncAll(ContentResolver contentResolver) {
        QueryMessagesService queryMessagesService = new QueryMessagesService(contentResolver);
        List<Message> messages = queryMessagesService.findAllSync();
        messages.forEach(this::syncMessage);
    }

    public void syncMessage(Message message) {
        if(messageNotExists(message)) {
            messageRepository.insertMessage(message);
        }
    }

    private Boolean messageNotExists(Message message) {
        return !messageExists(message);
    }

    private Boolean messageExists(Message message) {
        return messageRepository.findByInternalId(message.getInternalId()) != null;
    }
}
