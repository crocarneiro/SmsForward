package br.com.smsforward.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.smsforward.model.Origin;

@Dao
public interface OriginRepository {
    @Insert
    long insertOrigin(Origin origin);

    @Update
    int updateOrigin(Origin origin);

    @Delete
    int deleteOrigin(Origin origin);

    @Query("SELECT * FROM origins")
    List<Origin> findAllOrigins();

    @Query("SELECT * FROM origins WHERE id = :id")
    Origin findOriginById(Long id);

    @Query("SELECT * FROM origins WHERE address = :address")
    Origin findOriginByAddress(String address);
}
