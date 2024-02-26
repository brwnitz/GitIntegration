package com.example.gitintegration.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitintegration.Models.Repository;
import com.example.gitintegration.R;

import java.util.ArrayList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.MyViewHolder> {
    Context context;
    ArrayList<Repository> repos;
    ArrayList<Repository> displayedRepos;
    public RepoAdapter(Context context, ArrayList<Repository> repos){
        this.context = context;
        this.repos = repos;
        displayedRepos = new ArrayList<>();
    }

    @NonNull
    @Override
    public RepoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_repos, parent, false);
        return new RepoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.MyViewHolder holder, int position) {
        Repository repoAtual = repos.get(position);
        holder.textView.setText(repoAtual.getName());
        holder.buttonRepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(repoAtual.getHtml_url()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        Button buttonRepo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvRepo);
            buttonRepo = itemView.findViewById(R.id.btnRepo);
        }
    }
}
