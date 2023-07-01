
import org.junit.Assert.*
import org.junit.Test

class NotesRepositoryTest {
    private val repository = NotesRepository()

    @Test
    fun testCreateNote() {
        val note = repository.createNote("Title", "Text")
        assertNotNull(note)
        assertEquals("Title", note.title)
        assertEquals("Text", note.text)
    }

    @Test
    fun testEditNote() {
        val note = repository.createNote("Title", "Text")
        val editedNote = repository.editNote(note.id, "New Title", "New Text")
        assertNotNull(editedNote)
        assertEquals("New Title", editedNote!!.title)
        assertEquals("New Text", editedNote.text)
    }

    @Test
    fun testDeleteNote() {
        val note = repository.createNote("Title", "Text")
        val isDeleted = repository.deleteNote(note.id)
        assertTrue(isDeleted)
    }

    @Test
    fun testGetNotes() {
        repository.createNote("Title 1", "Text 1")
        repository.createNote("Title 2", "Text 2")
        val notes = repository.getNotes()
        assertEquals(2, notes.size)
        assertEquals("Title 1", notes[0].title)
        assertEquals("Text 1", notes[0].text)
        assertEquals("Title 2", notes[1].title)
        assertEquals("Text 2", notes[1].text)
    }

    @Test
    fun testAddComment() {
        val note = repository.createNote("Title", "Text")
        val comment = repository.addComment(note.id, "Comment 1")
        assertNotNull(comment)
        assertEquals("Comment 1", comment!!.message)
        assertEquals(note.id, comment.noteId)
    }

    @Test
    fun testEditComment() {
        val note = repository.createNote("Title", "Text")
        val comment = repository.addComment(note.id, "Comment 1")
        val editedComment = repository.editComment(note.id, comment!!.id, "Edited Comment")
        assertNotNull(editedComment)
        assertEquals("Edited Comment", editedComment!!.message)
        assertEquals(comment.id, editedComment.id)
        assertEquals(note.id, editedComment.noteId)
    }

    @Test
    fun testDeleteComment() {
        val note = repository.createNote("Title", "Text")
        val comment = repository.addComment(note.id, "Comment 1")
        val isDeleted = repository.deleteComment(note.id, comment!!.id)
        assertTrue(isDeleted)
    }

    @Test
    fun testRestoreComment() {
        val note = repository.createNote("Title", "Text")
        val comment = repository.addComment(note.id, "Comment 1")
        repository.deleteComment(note.id, comment!!.id)
        val isRestored = repository.restoreComment(note.id, comment.id)
        assertEquals(false, isRestored)
    }

    @Test
    fun testGetComments() {
        val note = repository.createNote("Title", "Text")
        repository.addComment(note.id, "Comment 1")
        repository.addComment(note.id, "Comment 2")
        repository.addComment(note.id, "Comment 3")
        repository.deleteComment(note.id, 2)
        val comments = repository.getComments(note.id)
        assertEquals(2, comments.size)
        assertEquals("Comment 1", comments[0].message)
        assertEquals("Comment 3", comments[1].message)
    }
}