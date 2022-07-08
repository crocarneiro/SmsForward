package br.com.smsforward.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import br.com.smsforward.model.Message;

@Dao
public interface MessageRepository {
    @Insert
    long insertMessage(Message message);

    @Update
    int updateMessage(Message message);

    @Delete
    int deleteMessage(Message message);

    @Query("SELECT * FROM messages")
    List<Message> findAll();

    @Query("SELECT * FROM messages WHERE internalId = :internalId")
    Message findByInternalId(long internalId);
}
