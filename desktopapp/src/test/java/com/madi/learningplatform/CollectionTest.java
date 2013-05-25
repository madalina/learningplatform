package com.madi.learningplatform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The class <code>CollectionTest</code> contains tests for the class <code>{@link Collection}</code>.
 *
 * @generatedBy CodePro at 5/9/13 11:37 AM
 * @author madi
 * @version $Revision: 1.0 $
 */
public class CollectionTest {
    /**
     * Run the Collection() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCollection_1()
        throws Exception {

        Collection result = new Collection();

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.toString());
        assertEquals(null, result.getName());
        assertEquals(null, result.getId());
    }

    /**
     * Run the Collection(String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCollection_2()
        throws Exception {
        String name = "";

        Collection result = new Collection(name);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.toString());
        assertEquals("", result.getName());
        assertEquals(null, result.getId());
    }

    /**
     * Run the Collection(ObjectId,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCollection_3()
        throws Exception {
        String name = "";
        Collection result = new Collection(-1, name);

        // add additional test code here
        assertNotNull(result);
        assertEquals("", result.toString());
        assertEquals("", result.getName());
    }

    /**
     * Run the void addNote(Note) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testAddNote_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());
        Note note = new Note();

        fixture.addNote(note);

        // add additional test code here
    }

    /**
     * Run the int compareTo(Collection) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testCompareTo_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());
        Collection o = new Collection();

        int result = fixture.compareTo(o);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.NullPointerException
        //       at com.madi.learningplatform.Collection.compareTo(Collection.java:53)
        assertEquals(-1, result);
    }

    /**
     * Run the String getName() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testGetName_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());

        String result = fixture.getName();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the Set<Note> getNotes() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testGetNotes_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());

        Set<Note> result = fixture.getNotes();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Run the void setName(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testSetName_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());
        String newName = "";

        fixture.setName(newName);

        // add additional test code here
    }

    /**
     * Run the void setNotes(Set<Note>) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testSetNotes_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());
        Set<Note> notes = new HashSet<Note>();

        fixture.setNotes(notes);

        // add additional test code here
    }

    /**
     * Run the String toString() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testToString_1()
        throws Exception {
        Collection fixture = new Collection(-1, "");
        fixture.setNotes(new HashSet<Note>());

        String result = fixture.toString();

        // add additional test code here
        assertEquals("", result);
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
        // add additional set up code here
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
        // Add additional tear down code here
    }

    /**
     * Launch the test.
     *
     * @param args the command line arguments
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(CollectionTest.class);
    }
}