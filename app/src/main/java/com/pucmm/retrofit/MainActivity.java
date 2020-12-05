package com.pucmm.retrofit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Comment> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<List<Comment>> listCall = retrofit.create(APIService.class).getCommentsByPost(20);
        listCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                elements = response.body();

                PostAdapter adapter = new PostAdapter();

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
//
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("onFailure: ", t.getMessage());
            }
        });

//        Call<List<Post>> listCall = retrofit.create(APIService.class).getPosts();
//
//        listCall.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//
//                elements = response.body();
//
//                PostAdapter adapter = new PostAdapter();
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setHasFixedSize(true);
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setAdapter(adapter);
//
//
//                //    Log.e("response", response.body())
//
//            }

//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//                Log.e("onFailure: ", t.getMessage());
//            }
//        });
    }

    private class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements Serializable {

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView _id;
            TextView title;
            TextView body;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                _id = itemView.findViewById(R.id._id);
                title = itemView.findViewById(R.id.title);
                body = itemView.findViewById(R.id.body);
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_item, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity.PostAdapter.MyViewHolder holder, int position) {
            Comment element = elements.get(position);
            holder._id.setText(String.valueOf(element.getId()));
            holder.title.setText(element.getName());
            holder.body.setText(element.getBody());
        }

        @Override
        public int getItemCount() {
            return elements.size();
        }


    }
}