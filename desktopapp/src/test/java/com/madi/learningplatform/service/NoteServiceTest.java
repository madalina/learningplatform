    package com.madi.learningplatform.service;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.madi.learningplatform.Collection;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.exceptions.CollectionDuplicateException;
import com.madi.learningplatform.exceptions.NoteNotFoundException;


/**
 * The class <code>NoteServiceTest</code> contains tests for the class <code>{@link NoteService}</code>.
 *
 * @generatedBy CodePro at 5/9/13 11:37 AM
 * @author madi
 * @version $Revision: 1.0 $
 */
public class NoteServiceTest {
    
    Logger log = Logger.getLogger(NoteServiceTest.class);
    CollectionService collectionService = new CollectionService();
    NoteService noteService = new NoteService();
    Collection selectedCollection;
    
    @Test
    public void testNoteService_1()
        throws Exception {
        NoteService result = new NoteService();
        assertNotNull(result);
    }

    /**
     * Run the void addNote(Note,Collection) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testAddNote_1()
        throws Exception {
        noteService.addNote(new Note("front", "back"), selectedCollection);
        Assert.assertEquals(noteService.countNotesInCollection(selectedCollection.getId()), 1);
        noteService.deleteNote(noteService.getNote("front", "back"));
    }

    /**
     * Run the void addNote(Note,Collection) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testAddNote_2()
        throws Exception {
        boolean throwsEx = false;
        try
        {
            noteService.addNote(new Note(null, null), selectedCollection);
        }
        catch(IllegalArgumentException ex)
        {
            throwsEx = true;
        }
        
        Assert.assertTrue(throwsEx);
        Assert.assertTrue(noteService.countNotesInCollection(selectedCollection.getId()) == 0);
    }

    /**
     * Run the int countNotesInCollection(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCountNotesInCollection_1()
        throws Exception {
        noteService.addNote(new Note("1", ""), selectedCollection);
        noteService.addNote(new Note("2", ""), selectedCollection);
        noteService.addNote(new Note("3", ""), selectedCollection);
        
        Assert.assertEquals(noteService.countNotesInCollection(selectedCollection.getId()), 3);
        
        noteService.deleteAllNotes(selectedCollection.getId());
    }

    /**
     * Run the int countUnlearnedNotesInCollection(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCountUnlearnedNotesInCollection()
        throws Exception {
        noteService.addNote(new Note("1", ""), selectedCollection);
        noteService.addNote(new Note("2", ""), selectedCollection);
        noteService.addNote(new Note("3", ""), selectedCollection);
        
        Assert.assertTrue(noteService.countUnlearnedNotesInCollection(selectedCollection.getId()) == 3);
        
        try
        {
            Note note = noteService.getNote("1", "");
            noteService.markLearned(note.getId());
            Assert.assertEquals(noteService.countUnlearnedNotesInCollection(selectedCollection.getId()), 2);
            noteService.deleteAllNotes(selectedCollection.getId());
        }
        catch(NoteNotFoundException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Run the void deleteNote(Note) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testDeleteNote_1()
        throws Exception {
        noteService.addNote(new Note("1", ""), selectedCollection);
        Assert.assertTrue(noteService.countNotesInCollection(selectedCollection.getId()) == 1);
        
        Note fetchedNote = noteService.getNote("1", "");
        noteService.deleteNote(fetchedNote);
        Assert.assertTrue(noteService.countNotesInCollection(selectedCollection.getId()) == 0);
        
        boolean throwsEx = false;
        try
        {
            noteService.deleteNote(new Note());
        }
        catch(NoteNotFoundException ex) 
        {
            throwsEx = true;
        }
        
        Assert.assertTrue(throwsEx);
    }

    /**
     * Run the void markLearned(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testMarkLearned_1()
        throws Exception {
        noteService.addNote(new Note("1", ""), selectedCollection);
        
        Assert.assertTrue(noteService.countUnlearnedNotesInCollection(selectedCollection.getId()) == 1);
        
        try
        {
            Note note = noteService.getNote("1", "");
            noteService.markLearned(note.getId());
            Assert.assertEquals(noteService.countUnlearnedNotesInCollection(selectedCollection.getId()), 0);
            noteService.deleteAllNotes(selectedCollection.getId());
        }
        catch(NoteNotFoundException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Run the void updateNote(Note) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testUpdateNote_1()
        throws Exception {
        
        noteService.addNote(new Note("1", ""), selectedCollection);
        Assert.assertTrue(noteService.countNotesInCollection(selectedCollection.getId()) == 1);
        
        try
        {
            Note note = noteService.getNote("1", "");
            note.setBack("updated");
            noteService.updateNote(note);
            Assert.assertEquals(noteService.countNotesInCollection(selectedCollection.getId()), 1);
            
            Note updatedNote = noteService.getNote("1", "updated");
            Assert.assertEquals(note.getId(), updatedNote.getId());
            Assert.assertEquals(updatedNote.getBack(), "updated");
            noteService.deleteAllNotes(selectedCollection.getId());
        }
        catch(NoteNotFoundException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Before
    public void setUp()
        throws Exception {
        try
        {
            collectionService.addCollection(new Collection("Test Collection"));
        }
        catch(CollectionDuplicateException ex)
        {
            
        }
        selectedCollection = collectionService.getCollection("Test Collection");
    }

    /**
     * Perform post-test clean-up.
     *
     * @throws Exception
     *         if the clean-up fails for some reason
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @After
    public void tearDown()
        throws Exception {
        noteService.deleteAllNotes(selectedCollection.getId());
        collectionService.deleteCollection(selectedCollection.getId());
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(NoteServiceTest.class);
    }
}