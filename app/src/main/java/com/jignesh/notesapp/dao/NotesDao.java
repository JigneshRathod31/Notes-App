package com.jignesh.notesapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jignesh.notesapp.models.NotesModel;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes_tbl")
    public LiveData<List<NotesModel>> getAllNotes();

    @Query("SELECT * FROM notes_tbl ORDER BY note_priority ASC")
    public LiveData<List<NotesModel>> getAllNotesLowToHigh();

    @Query("SELECT * FROM notes_tbl ORDER BY note_priority DESC")
    public LiveData<List<NotesModel>> getAllNotesHighToLow();

    @Insert
    public void insertNote(NotesModel notesModel);

    @Update
    public void updateNote(NotesModel notesModel);

    @Query("DELETE FROM notes_tbl WHERE id=:id")
    public void deleteNote(int id);
}
