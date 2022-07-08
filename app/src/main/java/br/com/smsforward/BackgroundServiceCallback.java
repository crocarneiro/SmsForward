package br.com.smsforward;

public interface BackgroundServiceCallback<T> {
    void onComplete(Result<T> result);
}