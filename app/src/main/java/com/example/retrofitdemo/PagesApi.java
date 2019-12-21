package com.example.retrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PagesApi {
    String BASE_URL = "https://reqres.in/api/";

    @GET("users?page=2")
    Call<Page> getPages();
}
