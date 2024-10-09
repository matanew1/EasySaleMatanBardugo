package com.example.easysalematanbardugo.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ProductDao {
    @Query("SELECT * FROM products_table")
    LiveData<List<ProductEntity>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductEntity product);

    @Delete
    void delete(ProductEntity product);
}
