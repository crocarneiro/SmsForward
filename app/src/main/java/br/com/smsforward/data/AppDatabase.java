package br.com.smsforward.data;

import androidx.room.RoomDatabase;

import br.com.smsforward.model.IntegrationHistory;
import br.com.smsforward.model.Message;
import br.com.smsforward.repositories.IntegrationHistoryRepository;
import br.com.smsforward.repositories.MessageRepository;

@androidx.room.Database(entities = { Message.class, IntegrationHistory.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IntegrationHistoryRepository integrationHistoryRepository();
    public abstract MessageRepository messageRepository();
}