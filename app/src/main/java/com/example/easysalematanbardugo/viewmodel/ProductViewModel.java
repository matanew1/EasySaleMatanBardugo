package com.example.easysalematanbardugo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.easysalematanbardugo.db.ProductEntity;
import com.example.easysalematanbardugo.repository.ProductRepository;

import java.util.List;


public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository repository;
    private final MutableLiveData<List<ProductEntity>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = new MutableLiveData<>();
        loadProducts();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    private void loadProducts() {
        // Observe changes in product data from the repository
        repository.getAllProducts().observeForever(products -> {
            if (products != null) {
                if (products.isEmpty()) {
                    fetchProductsFromApi();
                } else {
                    allProducts.postValue(products);
                }
            }
        });
    }

    private void fetchProductsFromApi() {
        repository.fetchUsersFromApi(products -> {
            if (products != null) {
                allProducts.postValue(products);
            }
        });
    }

    public void insertProduct(ProductEntity productEntity) {
        repository.insertProduct(productEntity);
    }

    public void deleteProduct(ProductEntity product) {
        repository.deleteProduct(product);
    }
}
