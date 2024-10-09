package com.example.easysalematanbardugo.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Entity class representing a user in the database.
 */
@Entity(tableName = "products_table")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private int id;
    private String title;
    private float price;
    private String description;
    private String category;
    private String image;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }


    @NonNull
    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}