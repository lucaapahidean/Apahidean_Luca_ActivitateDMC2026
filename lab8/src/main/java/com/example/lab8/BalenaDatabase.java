package com.example.lab8;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Balena.class}, version = 1)
public abstract class BalenaDatabase extends RoomDatabase {
    public abstract BalenaDAO balenaDAO();
}