package br.com.smsforward.model.message;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages", indices = {@Index(value = {"internalId"}, unique = true)})
public class Message {
    @PrimaryKey
    private Long id;

    // Fields from the android database
    private Long internalId;
    private String address;
    private String creator;
    private String person;
    private Long date;
    private Long dateSent;
    private String body;

    // Aditional fields I created
    private Boolean initial; /* True if the message already existed when the app ran for the first time */

    public Message(Long internalId,
                   String address,
                   String creator,
                   String person,
                   Long date,
                   Long dateSent,
                   String body,
                   Boolean initial) {
        this.internalId = internalId;
        this.address = address;
        this.creator = creator;
        this.person = person;
        this.date = date;
        this.dateSent = dateSent;
        this.body = body;
        this.initial = initial;
    }

    public Message(Long internalId,
                   String address,
                   String creator,
                   String person,
                   Long date,
                   Long dateSent,
                   String body) {
        this.internalId = internalId;
        this.address = address;
        this.creator = creator;
        this.person = person;
        this.date = date;
        this.dateSent = dateSent;
        this.body = body;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getDateSent() {
        return dateSent;
    }

    public void setDateSent(Long dateSent) {
        this.dateSent = dateSent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getInitial() {
        return initial;
    }

    public void setInitial(Boolean initial) {
        this.initial = initial;
    }
}
