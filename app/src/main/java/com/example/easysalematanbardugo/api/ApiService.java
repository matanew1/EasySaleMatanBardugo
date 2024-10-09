package com.example.easysalematanbardugo.api;

import com.example.easysalematanbardugo.db.ProductEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {

    @GET("products")
    Call<List<ProductEntity>> getProducts();

    @POST("products")
    Call<ProductEntity> addProduct(@Body ProductEntity product);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);
}
