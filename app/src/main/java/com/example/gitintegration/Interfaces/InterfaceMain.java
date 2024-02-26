package com.example.gitintegration.Interfaces;

import com.example.gitintegration.Models.Repository;
import com.example.gitintegration.Models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterfaceMain {
        @GET("{user}")
        Call<User> getData(@Path("user") String user);

        @GET("{user}/repos")
        Call<ArrayList<Repository>> getRepos(@Path("user") String user, @Query("page") int page);
}
