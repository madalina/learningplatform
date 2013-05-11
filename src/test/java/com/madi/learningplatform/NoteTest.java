package com.madi.learningplatform;

import java.text.DateFormat;
import java.util.Date;
import org.bson.types.ObjectId;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>NoteTest</code> contains tests for the class <code>{@link Note}</code>.
 *
 * @generatedBy CodePro at 5/9/13 11:36 AM
 * @author madi
 * @version $Revision: 1.0 $
 */
public class NoteTest {
    /**
     * Run the Note() constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testNote_1()
        throws Exception {

        Note result = new Note();

        // add additional test code here
        assertNotNull(result);
        assertEquals(null, result.getId());
        assertEquals("", result.getFront());
        assertEquals("", result.getBack());
        assertEquals(Boolean.FALSE, result.getLearned());
    }

    /**
     * Run the Note(String,String) constructor test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testNote_2()
        throws Exception {
        String front = "";
        String back = "";

        Note result = new Note(front, back);

        // add additional test code here
        assertNotNull(result);
        assertEquals(" : ; id null", result.toString());
        assertEquals(null, result.getId());
        assertEquals("", result.getFront());
        assertEquals("", result.getBack());
        assertEquals(Boolean.FALSE, result.getLearned());
    }

    /**
     * Run the int compareTo(Note) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testCompareTo_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        Note o = new Note();

        int result = fixture.compareTo(o);
        assertEquals(0, result);
    }

    /**
     * Run the String getBack() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetBack_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));

        String result = fixture.getBack();

        // add additional test code here
        assertEquals("", result);
    }

    /**
     * Run the String getFront() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetFront_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));

        String result = fixture.getFront();

        // add additional test code here
        assertEquals("", result);
    }

   
    /**
     * Run the Boolean getLearned() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testGetLearned_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));

        Boolean result = fixture.getLearned();

        // add additional test code here
        assertNotNull(result);
        assertEquals("true", result.toString());
        assertEquals(true, result.booleanValue());
    }

    /**
     * Run the void setBack(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetBack_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        String back = "";

        fixture.setBack(back);

        // add additional test code here
    }

    /**
     * Run the void setDateAdded(Date) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetDateAdded_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        Date dateAdded = new Date();

        fixture.setDateAdded(dateAdded);

        // add additional test code here
    }

    /**
     * Run the void setFront(String) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetFront_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        String front = "";

        fixture.setFront(front);

        // add additional test code here
    }

    /**
     * Run the void setId(ObjectId) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetId_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        ObjectId id = new ObjectId();

        fixture.setId(id);

        // add additional test code here
    }

    /**
     * Run the void setLearned(Boolean) method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testSetLearned_1()
        throws Exception {
        Note fixture = new Note(new ObjectId(), "", "", new Date(), new Boolean(true), new Boolean(false));
        Boolean learned = new Boolean(true);

        fixture.setLearned(learned);

        // add additional test code here
    }

    /**
     * Run the String toString() method test.
     *
     * @throws Exception
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    @Test
    public void testToString_1()
        throws Exception {
        Note fixture = new Note(new ObjectId("518b6e320364e8b3acf6c6a6"), "", "", new Date(), new Boolean(true), new Boolean(false));

        String result = fixture.toString();

        // add additional test code here
        assertEquals(" : ; id 518b6e320364e8b3acf6c6a6", result);
    }

    /**
     * Perform pre-test initialization.
     *
     * @throws Exception
     *         if the initialization fails for some reason
     *
     * @generatedBy CodePro at 5/9/13 11:36 AM
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
     * @generatedBy CodePro at 5/9/13 11:36 AM
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
     * @generatedBy CodePro at 5/9/13 11:36 AM
     */
    public static void main(String[] args) {
        new org.junit.runner.JUnitCore().run(NoteTest.class);
    }
}