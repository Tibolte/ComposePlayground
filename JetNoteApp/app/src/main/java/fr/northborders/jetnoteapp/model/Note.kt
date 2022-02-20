package fr.northborders.jetnoteapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "notes")
data class Note constructor(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    //val entryDate: Date = Date.from(Instant.now())
)
