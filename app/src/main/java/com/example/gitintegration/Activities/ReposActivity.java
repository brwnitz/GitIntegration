package com.example.gitintegration.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gitintegration.Adapters.RepoAdapter;
import com.example.gitintegration.Interfaces.InterfaceMain;
import com.example.gitintegration.Models.Repository;
import com.example.gitintegration.Models.User;
import com.example.gitintegration.MyClient;
import com.example.gitintegration.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposActivity extends AppCompatActivity {

    public User user;
    public ArrayList<Repository> repository;
    public int page;
    public ImageView imageProfile;
    public RecyclerView recyclerView;
    private Button[] buttons;
    private int buttonQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        recyclerView = findViewById(R.id.rvRepo);
        imageProfile = findViewById(R.id.imageProfile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = (User) extras.getSerializable("user");
            repository = (ArrayList<Repository>) extras.getSerializable("repository");
            page = (int) extras.getInt("page");
            String imagePicture = user.getAvatar_url();
            Picasso.get().load(imagePicture).into(imageProfile);
            createButtons(repository.size());
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = linearLayoutManager.getItemCount();

            }
        });
        repositoryMethod(user, 1);
    }

    public void repositoryMethod(User user, int page){
        InterfaceMain apiService = MyClient.getClient();

        Call<ArrayList<Repository>> call = apiService.getRepos(user.getLogin(),page);
        call.enqueue(new Callback<ArrayList<Repository>>() {
            @Override
            public void onResponse(Call<ArrayList<Repository>> call, Response<ArrayList<Repository>> response) {
                ArrayList<Repository> reposResponse = response.body();
                RepoAdapter adapter = new RepoAdapter(getApplicationContext(),repository);
                recyclerView.setAdapter(adapter);
                Log.d("REPOS",String.valueOf(reposResponse));
            }

            @Override
            public void onFailure(Call<ArrayList<Repository>> call, Throwable t) {
                Toast.makeText(ReposActivity.this,"texto",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createButtons(int repoSize){
        int val = repoSize % 30;
        val = val==0?0:1;
        buttonQuantity = repoSize/30 + val;
        LinearLayout linearLayout = findViewById(R.id.llButtons);

        buttons = new Button[buttonQuantity];
        for (int i = 0; i < buttonQuantity; i++) {
            buttons[i] = new Button(this);
            buttons[i].setText(String.valueOf(i+1));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.addView(buttons[i],layoutParams);
            final int j = i;
            buttons[j].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    repositoryMethod(user,(j+1));
                }
            });
        }
    }
}