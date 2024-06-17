package com.example.egear.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    public void insert(Cart... cart);
    @Update
    public void update(Cart... cart);
    @Delete
    public void delete(Cart cart);

    @Query("SELECT * FROM cart")
    public List<Cart> getCarts();
}
