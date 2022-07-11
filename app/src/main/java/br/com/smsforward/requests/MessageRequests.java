package br.com.smsforward.requests;

import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import br.com.smsforward.model.integration_destiny.IntegrationDestiny;
import br.com.smsforward.model.message.Message;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageRequests {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client;

    public MessageRequests() {
        this.client = new OkHttpClient();
    }

    public void postMessageTo(IntegrationDestiny integrationDestiny, Message message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String body;
        try {
            body = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw e;
        }

        RequestBody requestBody = RequestBody.create(body, JSON);
        Request.Builder builder = new Request.Builder()
                .post(requestBody)
                .url(integrationDestiny.getUrl());

        String headers = integrationDestiny.getHeaders();
        if(!headers.isEmpty()) {
            Arrays.stream(integrationDestiny.getHeaders().split(";")).forEach(header -> {
                header = header.trim();
                String key = header.split("=")[0].trim();
                String value = header.split("=")[1].trim();
                builder.header(key, value);
            });
        }

        Request request = builder.build();
        try (Response response = client.newCall(request).execute()) {
            if(response.isSuccessful())
                Log.i(getClass().getCanonicalName(), "Message successfully posted to URL.");
            else
                Log.e(getClass().getCanonicalName(), "Something went wrong posting message.");
        }
    }
}
