package com.example.gitintegration.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gitintegration.Interfaces.InterfaceMain;
import com.example.gitintegration.Models.Repository;
import com.example.gitintegration.Models.User;
import com.example.gitintegration.MyClient;
import com.example.gitintegration.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    User user = new User();
    Activity activity;
    private EditText textoTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonTeste = findViewById(R.id.buttonTeste);
        activity = this;
        textoTeste = findViewById(R.id.textoTeste);
        buttonTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { userMethod(); }
        });
    }

    public void userMethod(){
    InterfaceMain apiService = MyClient.getClient();
    Call<User> call = apiService.getData(textoTeste.getText().toString());
    call.enqueue(new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            User userResponse = response.body();
            Log.d("RESPOSTA",String.valueOf(userResponse));
            repositoryMethod(userResponse, 1);
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {

        }
    });
    }

    public void repositoryMethod(User user, int page){
        InterfaceMain apiService = MyClient.getClient();

        Call<ArrayList<Repository>> call = apiService.getRepos(textoTeste.getText().toString(), page);
        call.enqueue(new Callback<ArrayList<Repository>>() {
            @Override
            public void onResponse(Call<ArrayList<Repository>> call, Response<ArrayList<Repository>> response) {
                ArrayList<Repository> reposResponse = response.body();
                Log.d("REPOS",String.valueOf(reposResponse));
                goToReposActivity(reposResponse,user);
            }

            @Override
            public void onFailure(Call<ArrayList<Repository>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"texto",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void goToReposActivity(ArrayList<Repository> repository, User user){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        bundle.putSerializable("repository",repository);
        Intent intent = new Intent(this, ReposActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}