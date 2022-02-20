package fr.northborders.jetnoteapp.data

import androidx.room.*
import fr.northborders.jetnoteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id=:id")
    suspend fun getNote(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE from notes")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}