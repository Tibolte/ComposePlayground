package fr.northborders.jetnoteapp.repository

import fr.northborders.jetnoteapp.data.NoteDao
import fr.northborders.jetnoteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(val dao: NoteDao) {

    suspend fun addNote(note: Note) = dao.insert(note)

    suspend fun updateNote(note: Note) = dao.update(note)

    suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    suspend fun deleteAllNotes() = dao.deleteAll()

    fun getAllNotes(): Flow<List<Note>> = dao.getNotes().flowOn(Dispatchers.IO).conflate()
}