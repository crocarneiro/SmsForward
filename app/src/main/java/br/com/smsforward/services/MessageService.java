package br.com.smsforward.services;

import android.content.ContentResolver;
import android.util.Log;
import java.util.List;
import java.util.concurrent.Executor;
import br.com.smsforward.data.Database;
import br.com.smsforward.model.integration_history.IntegrationHistory;
import br.com.smsforward.model.message.Message;
import br.com.smsforward.repositories.IntegrationHistoryRepository;
import br.com.smsforward.repositories.MessageRepository;
import br.com.smsforward.requests.MessageRequests;
import br.com.smsforward.utils.time.DateTimeFactory;

public class MessageService {
    private final MessageRepository messageRepository;
    private final IntegrationHistoryRepository integrationHistoryRepository;
    private final IntegrationDestinyService integrationDestinyService;
    private final OriginService originService;

    public MessageService() {
        this.messageRepository = Database.getDatabase().messageRepository();
        this.integrationHistoryRepository = Database.getDatabase().integrationHistoryRepository();
        this.integrationDestinyService = new IntegrationDestinyService();
        this.originService = new OriginService();
    }

    public void integrateMessages(ContentResolver contentResolver) {
        QueryMessagesService queryMessagesService = new QueryMessagesService(contentResolver);
        List<Message> messages = queryMessagesService.findAllSync();
        messages.forEach(message -> {
            /**
             * If the message exists, then it was a message that already existed when the application
             * ran for the first time, or is a message that already is integrated.
             **/
            if(messageNotExists(message) && originIsValid(message.getAddress()))
                integrateMessage(message);
        });
    }

    /**
     * We should only send messages which address are registered as origins in the app. Thus,
     * if the address is in the database, then the origin is valid.
     * @param address The number of the sender of the message
     * @return true if the origin is valid, false otherwise
     */
    private boolean originIsValid(String address) {
        return originService.findOriginByAddress(address) != null;
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

    public int deleteMessage(Message message) {
        return messageRepository.deleteMessage(message);
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
        message.setId(newMessageId);

        integrationDestinyService.findAllIntegrationDestinies().forEach(integrationDestiny -> {
            try {
                MessageRequests messageRequests = new MessageRequests();
                messageRequests.postMessageTo(integrationDestiny, message);
                IntegrationHistory integrationHistory = new IntegrationHistory(
                        DateTimeFactory.now(),
                        message.getId(),
                        message.getInternalId(),
                        integrationDestiny.getUrl()
                );
                integrationHistoryRepository.insertIntegrationHistory(integrationHistory);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(getClass().getCanonicalName(), "Error integrating message, deleting it from database in order to retry again later.");
                deleteMessage(message);
            }
        });
    }
}