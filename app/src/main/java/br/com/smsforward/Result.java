package br.com.smsforward;

public class Result<T> {
    private T data;
    private Boolean success;
    private Exception error;

    public Result(T data) {
        this.data = data;
        this.success = true;
    }

    public Result(Exception e) {
        this.success = false;
        this.error = e;
    }

    public T getData() {
        return data;
    }

    public Boolean success() {
        return success;
    }

    public Exception getError() {
        return error;
    }
}
