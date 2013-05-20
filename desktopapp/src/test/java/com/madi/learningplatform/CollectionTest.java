package com.madi.learningplatform;

import java.util.HashSet;
import java.util.Set;
import org.bson.types.ObjectId;
import org.junit.*;
import static org.junit.Assert.*;

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
        ObjectId id = new ObjectId();
        String name = "";

        Collection result = new Collection(id, name);

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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());
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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());
        Collection o = new Collection();

        int result = fixture.compareTo(o);

        // add additional test code here
        // An unexpected exception was thrown in user code while executing this test:
        //    java.lang.NullPointerException
        //       at com.madi.learningplatform.Collection.compareTo(Collection.java:53)
        assertEquals(-1, result);
    }

    /**
     * Run the ObjectId getId() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testGetId_1()
        throws Exception {
        Collection fixture = new Collection(new ObjectId("518b6e400364ee55d8ed0df7"), "");
        fixture.setNotes(new HashSet());

        ObjectId result = fixture.getId();

        // add additional test code here
        assertNotNull(result);
        assertEquals("518b6e400364ee55d8ed0df7", result.toString());
        assertEquals(1368092224000L, result.getTime());
        assertEquals(-655553033, result._inc());
        assertEquals(1368092224, result._time());
        assertEquals(56946261, result._machine());
        assertEquals("55ee6403406e8b51f70dedd8", result.toStringBabble());
        assertEquals("518b6e400364ee55d8ed0df7", result.toStringMongod());
        assertEquals(56946261, result.getMachine());
        assertEquals(-655553033, result.getInc());
        assertEquals(1368092224, result.getTimeSecond());
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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());

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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());

        Set<Note> result = fixture.getNotes();

        // add additional test code here
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Run the void setId(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:37 AM
     */
    @Test
    public void testSetId_1()
        throws Exception {
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());
        ObjectId id = new ObjectId();

        fixture.setId(id);

        // add additional test code here
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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());
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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());
        Set<Note> notes = new HashSet();

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
        Collection fixture = new Collection(new ObjectId(), "");
        fixture.setNotes(new HashSet());

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