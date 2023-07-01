
data class Note(
    val id: Int,
    var title: String,
    var text: String,
    val comments: MutableList<Comment> = mutableListOf(),
    val deletedComments: MutableSet<Int> = mutableSetOf()
)


data class Comment(
    val id: Int,
    val noteId: Int,
    var message: String,
    val deleted: Boolean = false
)


class NotesRepository {
    private val notes: MutableList<Note> = mutableListOf()

    fun createNote(title: String, text: String): Note {
        val newId = notes.size + 1
        val newNote = Note(newId, title, text)
        notes.add(newNote)
        return newNote
    }

    fun editNote(noteId: Int, title: String, text: String): Note? {
        val note = findNoteById(noteId)
        if (note != null) {
            note.title = title
            note.text = text
        }
        return note
    }

    fun deleteNote(noteId: Int): Boolean {
        val note = findNoteById(noteId)
        if (note != null) {
            notes.remove(note)
            return true
        }
        return false
    }

    fun getNotes(): List<Note> {
        return notes.toList()
    }

    private fun findNoteById(noteId: Int): Note? {
        return notes.find { it.id == noteId }
    }

    // Методы для работы с комментариями
    fun addComment(noteId: Int, message: String): Comment? {
        val note = findNoteById(noteId)
        if (note != null) {
            val newId = note.comments.size + 1
            val newComment = Comment(newId, noteId, message)
            note.comments.add(newComment)
            return newComment
        }
        return null
    }

    fun editComment(noteId: Int, commentId: Int, message: String): Comment? {
        val note = findNoteById(noteId)
        if (note != null) {
            val comment = note.comments.find { it.id == commentId }
            if (comment != null) {
                comment.message = message
                return comment
            }
        }
        return null
    }

    fun deleteComment(noteId: Int, commentId: Int): Boolean {
        val note = findNoteById(noteId)
        if (note != null) {
            val comment = note.comments.find { it.id == commentId }
            if (comment != null) {
                note.comments.remove(comment)
                note.deletedComments.add(commentId)
                return true
            }
        }
        return false
    }

    fun restoreComment(noteId: Int, commentId: Int): Boolean {
        val note = findNoteById(noteId)
        if (note != null) {
            val comment = note.comments.find { it.id == commentId && it.deleted }
            if (comment != null) {
                note.deletedComments.remove(commentId)
                return true
            }
        }
        return false
    }

    fun getComments(noteId: Int): List<Comment> {
        val note = findNoteById(noteId)
        return note?.comments?.filterNot { it.deleted } ?: emptyList()
    }
}