package br.com.smsforward.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.time.LocalDateTime;
import br.com.smsforward.data.TimestampConverter;

@Entity(tableName = "integration_history",
        indices = { @Index(value = {"internalMessageId"}, unique = true)}
)
public class IntegrationHistory {
    @PrimaryKey
    private Long id;

    @TypeConverters({TimestampConverter.class})
    private LocalDateTime syncDate;
    private Long messageId; // Message.id
    private Long internalMessageId; // Message._id
    private String sentToUrl;

    public IntegrationHistory() {
    }

    public IntegrationHistory(LocalDateTime syncDate, Long messageId, Long internalMessageId, String sentToUrl) {
        this.syncDate = syncDate;
        this.messageId = messageId;
        this.internalMessageId = internalMessageId;
        this.sentToUrl = sentToUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(LocalDateTime syncDate) {
        this.syncDate = syncDate;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getInternalMessageId() {
        return internalMessageId;
    }

    public void setInternalMessageId(Long internalMessageId) {
        this.internalMessageId = internalMessageId;
    }

    public String getSentToUrl() {
        return sentToUrl;
    }

    public void setSentToUrl(String sentToUrl) {
        this.sentToUrl = sentToUrl;
    }
}
