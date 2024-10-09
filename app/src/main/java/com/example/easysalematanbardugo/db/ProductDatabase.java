package com.example.easysalematanbardugo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {

    private static volatile ProductDatabase INSTANCE;

    public static ProductDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.
                            databaseBuilder(context.getApplicationContext(),
                                    ProductDatabase.class, "product_database")
                            .build();
                }
            }
        }
        return INSTANCE; // Return the singleton instance of the database
    }

    public abstract ProductDao productDao();
}
