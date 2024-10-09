package com.example.easysalematanbardugo.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.easysalematanbardugo.api.ApiClient;
import com.example.easysalematanbardugo.api.ApiService;
import com.example.easysalematanbardugo.db.ProductDao;
import com.example.easysalematanbardugo.db.ProductDatabase;
import com.example.easysalematanbardugo.db.ProductEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductRepository {

    private final ProductDao productDao;
    private final ApiService apiService;
    private final ExecutorService executorService;

    public ProductRepository(Application application) {
        // Get the instance of the Room database and initialize UserDao
        ProductDatabase db = ProductDatabase.getDatabase(application);
        productDao = db.productDao();

        // Initialize ApiService for network requests
        apiService = ApiClient.getApiService();

        // Initialize ExecutorService for background operations
        // 3 threads are used for parallel execution
        executorService = Executors.newFixedThreadPool(3);
    }

    public void fetchUsersFromApi(FetchProductCallback fetchProductCallback) {
        apiService.getProducts().enqueue(new Callback<List<ProductEntity>>() {

            @Override
            public void onResponse(@NonNull Call<List<ProductEntity>> call, @NonNull Response<List<ProductEntity>> response) {
                if (response.isSuccessful()) {
                    List<ProductEntity> products = response.body();
                    if (products != null) {
                        executorService.execute(() -> products.forEach(productDao::insert));
                        fetchProductCallback.onProductFetched(products);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductEntity>> call, @NonNull Throwable t) {
                fetchProductCallback.onProductFetched(null);
            }
        });
    }

    // operations
    public LiveData<List<ProductEntity>> getAllProducts() {
        return productDao.getAllProducts();
    }
    public void insertProduct(ProductEntity productEntity) {
        apiService.addProduct(productEntity).enqueue(new Callback<ProductEntity>() {
            @Override
            public void onResponse(@NonNull Call<ProductEntity> call, @NonNull Response<ProductEntity> response) {
                if (response.isSuccessful()) {
                    ProductEntity product = response.body();
                    if (product != null) {
                        executorService.execute(() -> productDao.insert(product));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductEntity> call, @NonNull Throwable t) {
                executorService.execute(() -> productDao.insert(productEntity));
            }
        });
//        executorService.execute(() -> productDao.insert(productEntity));
    }
    public void deleteProduct(@NonNull ProductEntity product) {
        apiService.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    executorService.execute(() -> productDao.delete(product));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                executorService.execute(() -> productDao.delete(product));
            }
        });
    }

    // Callback interface for fetching users
    public interface FetchProductCallback {
        void onProductFetched(List<ProductEntity> products);
    }

}
