package com.example.hhhelper;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient;

import java.io.IOException;

public class BackendRequest {
    static final String base_url = "localhost:5000";  // TODO: set
    String uri = "/";
    OkHttpClient client;

    static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    BackendRequest(String uri) {
        this.uri = uri;
        this.client = new OkHttpClient();
    }

    JSONObject get() {
        Request request = new Request.Builder()
                .url(base_url + uri)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    JSONObject post(JSONObject json) {
        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(base_url + uri)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    JSONObject put(JSONObject json) {
        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(base_url + uri)
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    JSONObject delete(JSONObject json) {
        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(base_url + uri)
                .delete(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new JSONObject(response.body().string());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
