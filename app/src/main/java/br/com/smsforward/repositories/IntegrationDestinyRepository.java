package br.com.smsforward.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.smsforward.model.integration_destiny.IntegrationDestiny;

@Dao
public interface IntegrationDestinyRepository {
    @Insert
    long insertIntegrationDestiny(IntegrationDestiny integrationDestiny);

    @Update
    int updateIntegrationDestiny(IntegrationDestiny integrationDestiny);

    @Delete
    int deleteIntegrationDestiny(IntegrationDestiny integrationDestiny);

    @Query("SELECT * FROM integration_destinies")
    List<IntegrationDestiny> findAllIntegrationDestinies();

    @Query("SELECT * FROM integration_destinies WHERE id = :id")
    IntegrationDestiny findIntegrationDestinyById(long id);

    @Query("SELECT * FROM integration_destinies WHERE url = :url")
    IntegrationDestiny findIntegrationDestinyByUrl(String url);
}
