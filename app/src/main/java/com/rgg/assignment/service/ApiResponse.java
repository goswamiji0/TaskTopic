package com.rgg.assignment.service;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

/**
 * Common class used by API responses.
 * It uses a class called ApiResponse for network requests.
 * ApiResponse is a simple wrapper around the Retrofit2.Call class
 *
 * @param <T>
 */
public class ApiResponse<T> {
    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Log.e(ignored.getMessage(), "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

}