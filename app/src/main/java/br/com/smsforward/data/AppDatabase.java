package br.com.smsforward.data;

import androidx.room.RoomDatabase;

import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.model.integration_history.IntegrationHistory;
import br.com.smsforward.model.message.Message;
import br.com.smsforward.model.origin.Origin;
import br.com.smsforward.repositories.IntegrationDestinyRepository;
import br.com.smsforward.repositories.IntegrationHistoryRepository;
import br.com.smsforward.repositories.MessageRepository;
import br.com.smsforward.repositories.OriginRepository;

@androidx.room.Database(entities = {
        Message.class,
        IntegrationHistory.class,
        IntegrationDestiny.class,
        Origin.class
        }, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IntegrationHistoryRepository integrationHistoryRepository();
    public abstract IntegrationDestinyRepository integrationDestinyRepository();
    public abstract MessageRepository messageRepository();
    public abstract OriginRepository originRepository();
}