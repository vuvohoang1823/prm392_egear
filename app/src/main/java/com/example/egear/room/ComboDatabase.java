package com.example.egear.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Combo.class}, version = 1)
public abstract class ComboDatabase extends RoomDatabase {
    public abstract ComboDAO getComboDAO();

}
