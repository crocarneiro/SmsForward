package br.com.smsforward.model.integration_destiny;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "integration_destinies",
        indices = { @Index(value = {"url"}, unique = true)})
public class IntegrationDestiny {
    @PrimaryKey
    private Long id;

    private String url;
    private String headers;

    public IntegrationDestiny() { }

    public IntegrationDestiny(String url, String headers) {
        this.url = url;
        this.headers = headers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }
}
