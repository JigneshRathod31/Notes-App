package com.jignesh.notesapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jignesh.notesapp.dao.NotesDao;
import com.jignesh.notesapp.models.NotesModel;

@Database(entities = {NotesModel.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

    public static NotesDatabase INSTANCE;

    public static NotesDatabase getNotesDatabaseInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, NotesDatabase.class, "notes_tbl").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }
}
