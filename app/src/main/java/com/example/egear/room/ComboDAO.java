package com.example.egear.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ComboDAO {
    @Insert
    public void insert(Combo... combo);
    @Update
    public void update(Combo... combo);
    @Delete
    public void delete(Combo combo);
    @Query("SELECT * FROM combo")
    public List<Combo> getCombos();
    @Query("SELECT * FROM combo WHERE id = :id")
    public Combo getComboById(Long id);
}
