package br.com.smsforward.services;

import android.content.ContentResolver;
import android.util.Log;
import java.util.List;
import java.util.concurrent.Executor;
import br.com.smsforward.data.Database;
import br.com.smsforward.model.IntegrationHistory;
import br.com.smsforward.model.Message;
import br.com.smsforward.repositories.IntegrationHistoryRepository;
import br.com.smsforward.repositories.MessageRepository;
import br.com.smsforward.utils.time.DateTimeFactory;

public class MessageService {
    private final MessageRepository messageRepository;
    private final IntegrationHistoryRepository integrationHistoryRepository;

    public MessageService() {
        messageRepository = Database.getDatabase().messageRepository();
        integrationHistoryRepository = Database.getDatabase().integrationHistoryRepository();
    }

    public void integrateMessages(ContentResolver contentResolver) {
        QueryMessagesService queryMessagesService = new QueryMessagesService(contentResolver);
        List<Message> messages = queryMessagesService.findAllSync();
        messages.forEach(message -> {
            /**
             * If the message exists, then it was a message that already existed when the application
             * ran for the first time, or is a message that already is integrated.
             **/
            if(messageNotExists(message))
                integrateMessage(message);
        });
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

    public long syncMessage(Message message) {
        if(messageNotExists(message)) {
            return messageRepository.insertMessage(message);
        }

        return 0;
    }

    private Boolean messageNotExists(Message message) {
        return !messageExists(message);
    }

    private Boolean messageExists(Message message) {
        return messageRepository.findByInternalId(message.getInternalId()) != null;
    }

    private Boolean messageIsNotIntegrated(Message message) {
        return !messageIsIntegrated(message);
    }

    private Boolean messageIsIntegrated(Message message) {
        return integrationHistoryRepository.findByInternalMessageId(message.getInternalId()) != null;
    }

    private void integrateMessage(Message message) {
        Log.i(getClass().getCanonicalName(), "Here we are going to integrate the message " + message.getInternalId());
        long newMessageId = syncMessage(message);
        IntegrationHistory integrationHistory = new IntegrationHistory(
                DateTimeFactory.now(),
                newMessageId,
                message.getInternalId(),
                ""
        );
        integrationHistoryRepository.insertIntegrationHistory(integrationHistory);
    }
}