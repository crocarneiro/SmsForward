package br.com.smsforward.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.smsforward.model.integration_history.IntegrationHistory;

@Dao
public interface IntegrationHistoryRepository {
    @Insert
    long insertIntegrationHistory(IntegrationHistory integrationHistory);

    @Update
    int updateIntegrationHistory(IntegrationHistory integrationHistory);

    @Delete
    int deleteIntegrationHistory(IntegrationHistory integrationHistory);

    @Query("SELECT * FROM integration_history")
    List<IntegrationHistory> findAll();

    @Query("SELECT * FROM integration_history WHERE internalMessageId = :internalMessageId")
    IntegrationHistory findByInternalMessageId(long internalMessageId);
}
