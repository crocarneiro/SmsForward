package br.com.smsforward.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "origins",
        indices = {@Index(value = {"address"}, unique = true)})
public class Origin {
    @PrimaryKey
    private Long id;
    private String address;

    public Origin() {
    }

    public Origin(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
