package com.example.movies_nicolay_nacaro.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ticket.class}, version=1, exportSchema = false)
public abstract class tDatabase extends RoomDatabase {

    public abstract TicketDAO TicketDAO();
    private static volatile tDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 3;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static tDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (tDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    tDatabase.class, "mydb")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
