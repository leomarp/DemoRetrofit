package com.pucmm.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface APIService {

   //nos regresa una lista de post
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getCommentsByPost(@Path("postId") int postId);

}
